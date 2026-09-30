import json
import re

with open("/tmp/cocktaildb_drinks.json", "r") as f:
    cdb = json.load(f)

with open("app/src/main/java/com/example/data/local/DatabaseSeeder.kt", "r") as f:
    content = f.read()

cocktail_pattern = re.compile(r"CocktailEntity\((.*?)\n    \)", re.DOTALL)
cocktails = cocktail_pattern.findall(content)

matched = {}
unmatched = []

for c in cocktails:
    name = re.search(r"name = \"([^\"]+)\"", c).group(1)
    nl = name.lower()
    if nl in cdb:
        matched[name] = cdb[nl]
    else:
        # try simple cleaning
        clean_nl = nl.replace("'", "").replace("’", "").replace("-", " ")
        found = False
        for k, v in cdb.items():
            clean_k = k.replace("'", "").replace("’", "").replace("-", " ")
            if clean_k == clean_nl:
                matched[name] = v
                found = True
                break
        if not found:
            unmatched.append(name)

print(f"Matched: {len(matched)} / {len(cocktails)}")
print(f"Unmatched: {len(unmatched)}")
print(unmatched)
