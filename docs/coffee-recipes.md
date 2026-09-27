# Bundled coffee recipes

`app/src/main/assets/coffee_recipes.json` is a snapshot of the public response from
https://recipes.theweldercatherine.ru/api/v1/recipes, downloaded on 2026-09-27.
It contains 1,683 recipe records from The Welder Catherine.
The original brewing parameters and notes are retained for future detail screens.

The recipe repository first requests the configured network source. If the request
fails, takes longer than five seconds, or returns an empty list, it loads this
snapshot off the main thread. Cancelling the screen's request does not trigger a
fallback. Local loading errors are reported through the existing error state.

The home screen groups the bundled entries under “Кофе” and shows their names and
total count. These entries do not open the existing mock food detail screen.
Successful API responses retain their existing navigation. The `mocked` build
continues to use its successful, deterministic mock network response.

To update the snapshot, replace the asset with a response from the same endpoint
and update the snapshot assertions in `BundledCoffeeRecipeDataSourceTest`.
Unit tests read the actual asset through the test resource source set.
