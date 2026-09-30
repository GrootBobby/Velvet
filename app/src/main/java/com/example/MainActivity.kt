package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.data.local.AppDatabase
import com.example.data.local.UserPreferencesRepository
import com.example.data.repository.CocktailRepository
import com.example.data.repository.GeminiRepository
import com.example.data.repository.InventoryRepository
import com.example.data.repository.ProfileRepository
import com.example.ui.bar.BarScreen
import com.example.ui.bar.BarViewModel
import com.example.ui.catalog.CatalogIntent
import com.example.ui.catalog.CatalogScreen
import com.example.ui.catalog.CatalogViewModel
import com.example.ui.components.ProfilePhotoSelectionDialog
import com.example.ui.components.VelvetBottomNavigation
import com.example.ui.components.VelvetTopBar
import com.example.ui.detail.DetailIntent
import com.example.ui.detail.DetailScreen
import com.example.ui.detail.DetailViewModel
import com.example.ui.friends.FriendsScreen
import com.example.ui.friends.FriendsViewModel
import com.example.ui.navigation.Screen
import com.example.ui.onboarding.OnboardingScreen
import com.example.ui.party.PartyHubScreen
import com.example.ui.prevention.BacCalculatorBottomSheet
import com.example.ui.prevention.BacCalculatorViewModel
import com.example.ui.party.UndercoverViewModel
import com.example.ui.profile.ProfileScreen
import com.example.ui.profile.ProfileViewModel
import com.example.ui.theme.VelvetSurface
import com.example.ui.theme.VelvetTheme
import com.example.ui.web.WebPwaView
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getInstance(applicationContext)
        val userPreferencesRepository = UserPreferencesRepository(applicationContext)
        val cocktailRepository = CocktailRepository(database.cocktailDao())
        val inventoryRepository = InventoryRepository(database.inventoryDao())
        val geminiRepository = GeminiRepository()
        val profileRepository = ProfileRepository(database.profileDao())
        val authManager = com.example.data.firebase.FirebaseAuthManager(applicationContext)
        val cloudSyncRepository = com.example.data.firebase.CloudSyncRepository(
            context = applicationContext,
            userPreferencesRepository = userPreferencesRepository,
            cocktailRepository = cocktailRepository,
            inventoryRepository = inventoryRepository
        )

        setContent {
            VelvetTheme {
                var isWebMode by remember { mutableStateOf(true) }
                if (isWebMode) {
                    Box(modifier = Modifier.fillMaxSize().background(VelvetSurface)) {
                        WebPwaView(modifier = Modifier.fillMaxSize())
                    }
                } else {
                    VelvetApp(
                        cocktailRepository = cocktailRepository,
                        inventoryRepository = inventoryRepository,
                        geminiRepository = geminiRepository,
                        profileRepository = profileRepository,
                        userPreferencesRepository = userPreferencesRepository,
                        authManager = authManager,
                        cloudSyncRepository = cloudSyncRepository
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VelvetApp(
    cocktailRepository: CocktailRepository,
    inventoryRepository: InventoryRepository,
    geminiRepository: GeminiRepository,
    profileRepository: ProfileRepository,
    userPreferencesRepository: UserPreferencesRepository,
    authManager: com.example.data.firebase.FirebaseAuthManager? = null,
    cloudSyncRepository: com.example.data.firebase.CloudSyncRepository? = null
) {
    val coroutineScope = rememberCoroutineScope()
    val userProgress by userPreferencesRepository.userProgress.collectAsState(initial = null)

    // Check if onboarding is needed on first launch
    if (userProgress != null && !userProgress!!.isOnboardingCompleted) {
        OnboardingScreen(
            onComplete = { firstName ->
                coroutineScope.launch {
                    userPreferencesRepository.completeOnboarding(firstName)
                }
            }
        )
        return
    }

    var currentScreen by remember { mutableStateOf<Screen>(Screen.MonBar) }
    var previousScreen by remember { mutableStateOf<Screen>(Screen.MonBar) }

    val barViewModel = remember {
        BarViewModel(inventoryRepository, cocktailRepository, userPreferencesRepository)
    }
    val catalogViewModel = remember {
        CatalogViewModel(cocktailRepository)
    }
    val detailViewModel = remember {
        DetailViewModel(cocktailRepository, geminiRepository, userPreferencesRepository)
    }
    val undercoverViewModel = remember {
        UndercoverViewModel(geminiRepository)
    }
    val profileViewModel = remember {
        ProfileViewModel(
            profileRepository = profileRepository,
            cocktailRepository = cocktailRepository,
            userPreferencesRepository = userPreferencesRepository,
            inventoryRepository = inventoryRepository,
            authManager = authManager,
            cloudSyncRepository = cloudSyncRepository
        )
    }
    val friendsViewModel = remember {
        FriendsViewModel(
            authManager = authManager,
            cloudSyncRepository = cloudSyncRepository,
            userPreferencesRepository = userPreferencesRepository
        )
    }
    val bacCalculatorViewModel = remember { BacCalculatorViewModel() }

    val barUiState by barViewModel.uiState.collectAsState()
    val catalogUiState by catalogViewModel.uiState.collectAsState()
    val detailUiState by detailViewModel.uiState.collectAsState()
    val undercoverUiState by undercoverViewModel.uiState.collectAsState()
    val profileUiState by profileViewModel.uiState.collectAsState()
    val friendsUiState by friendsViewModel.uiState.collectAsState()
    val bacCalculatorUiState by bacCalculatorViewModel.uiState.collectAsState()

    var showBacCalculatorSheet by remember { mutableStateOf(false) }
    val bacSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Handle back button for sub-screens / details
    BackHandler(enabled = currentScreen != Screen.MonBar) {
        if (currentScreen is Screen.Detail || currentScreen is Screen.Friends) {
            currentScreen = previousScreen
        } else {
            currentScreen = Screen.MonBar
        }
    }

    val isDetailScreen = currentScreen is Screen.Detail
    val isFriendsScreen = currentScreen is Screen.Friends
    var showTopBarPhotoDialog by remember { mutableStateOf(false) }

    if (showTopBarPhotoDialog) {
        ProfilePhotoSelectionDialog(
            onDismiss = { showTopBarPhotoDialog = false },
            onPhotoSelected = { uriString ->
                coroutineScope.launch {
                    userPreferencesRepository.setProfilePhotoUri(uriString)
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(VelvetSurface),
        topBar = {
            if (!isDetailScreen) {
                VelvetTopBar(
                    title = when (currentScreen) {
                        is Screen.MonBar -> null
                        is Screen.Recettes -> "Recettes"
                        is Screen.JeuxParty -> "Jeux Party"
                        is Screen.Profil -> "Mon Profil"
                        is Screen.Friends -> "Amis & Classement"
                        else -> null
                    },
                    avatarUrl = userProgress?.profilePhotoUri,
                    showBackButton = isFriendsScreen,
                    onBackClick = {
                        currentScreen = previousScreen
                    },
                    onFriendsClick = if (!isFriendsScreen) {
                        {
                            previousScreen = currentScreen
                            currentScreen = Screen.Friends
                        }
                    } else null,
                    onBacCalculatorClick = {
                        showBacCalculatorSheet = true
                    },
                    onProfileClick = {
                        previousScreen = currentScreen
                        currentScreen = Screen.Profil
                    },
                    modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                )
            }
        },
        bottomBar = {
            if (!isDetailScreen) {
                VelvetBottomNavigation(
                    currentScreen = currentScreen,
                    onTabSelected = { selected ->
                        previousScreen = currentScreen
                        currentScreen = selected
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(VelvetSurface)
        ) {
            when (val screen = currentScreen) {
                is Screen.MonBar -> {
                    BarScreen(
                        uiState = barUiState,
                        onIntent = barViewModel::processIntent,
                        onNavigateToCatalog = { categoryFilter ->
                            if (categoryFilter != null) {
                                catalogViewModel.processIntent(CatalogIntent.SelectCategory(categoryFilter))
                            }
                            previousScreen = Screen.MonBar
                            currentScreen = Screen.Recettes
                        },
                        onNavigateToCocktail = { id ->
                            detailViewModel.processIntent(DetailIntent.LoadCocktail(id))
                            previousScreen = Screen.MonBar
                            currentScreen = Screen.Detail(id)
                        },
                        onNavigateToParty = {
                            previousScreen = Screen.MonBar
                            currentScreen = Screen.JeuxParty
                        }
                    )
                }

                is Screen.Recettes -> {
                    CatalogScreen(
                        uiState = catalogUiState,
                        onIntent = catalogViewModel::processIntent,
                        onCocktailClick = { id ->
                            detailViewModel.processIntent(DetailIntent.LoadCocktail(id))
                            previousScreen = Screen.Recettes
                            currentScreen = Screen.Detail(id)
                        }
                    )
                }

                is Screen.JeuxParty -> {
                    PartyHubScreen(
                        undercoverUiState = undercoverUiState,
                        onUndercoverIntent = undercoverViewModel::processIntent
                    )
                }

                is Screen.Profil -> {
                    ProfileScreen(
                        uiState = profileUiState,
                        onIntent = profileViewModel::processIntent,
                        onCocktailClick = { id ->
                            detailViewModel.processIntent(DetailIntent.LoadCocktail(id))
                            previousScreen = Screen.Profil
                            currentScreen = Screen.Detail(id)
                        }
                    )
                }

                is Screen.Detail -> {
                    DetailScreen(
                        uiState = detailUiState,
                        onIntent = detailViewModel::processIntent,
                        onBackClick = {
                            currentScreen = previousScreen
                        }
                    )
                }

                is Screen.Friends -> {
                    FriendsScreen(
                        uiState = friendsUiState,
                        onIntent = friendsViewModel::processIntent,
                        onOpenAuth = {
                            profileViewModel.processIntent(com.example.ui.profile.ProfileIntent.OpenAuthDialog)
                            previousScreen = Screen.Friends
                            currentScreen = Screen.Profil
                        },
                        onCocktailClick = { id ->
                            detailViewModel.processIntent(DetailIntent.LoadCocktail(id))
                            previousScreen = Screen.Friends
                            currentScreen = Screen.Detail(id)
                        }
                    )
                }
            }
        }
    }

    if (showBacCalculatorSheet) {
        BacCalculatorBottomSheet(
            uiState = bacCalculatorUiState,
            onIntent = bacCalculatorViewModel::processIntent,
            sheetState = bacSheetState,
            onDismissRequest = { showBacCalculatorSheet = false }
        )
    }
}
