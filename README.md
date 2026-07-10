# Ofuse (お布施)

A tiny Android library that gives any app a ready-made "support the
developer" entry point — a donation/tip link (e.g. Ko-fi), opened in a
Custom Tab. No in-app purchase flow, no Play Billing integration: just an
external link, which is the standard, Play-policy-safe pattern for
no-strings-attached tips.

Published on [JitPack](https://jitpack.io/#AlienAsRoger/ofuse).

## Installation

Add the JitPack repository in your project's `settings.gradle.kts`
(under `dependencyResolutionManagement`, not a module's `build.gradle.kts` —
required if your project uses `FAIL_ON_PROJECT_REPOS`):

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency to your app/feature module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.AlienAsRoger:ofuse:0.1.0")
}
```

## Usage

Drop `SupportDeveloperRow` into any `Column`/`LazyColumn` inside your own
`MaterialTheme` — it renders as a standard settings-style `ListItem`:

```kotlin
SupportDeveloperRow(
    supportUrl = "https://ko-fi.com/yourhandle",
)
```

Optional parameters:

```kotlin
SupportDeveloperRow(
    supportUrl = "https://ko-fi.com/yourhandle",
    title = "Support Makoto",
    description = "Buy me a coffee",
    icon = Icons.Default.Favorite, // any ImageVector, or null for none
)
```

Tapping the row opens `supportUrl` in a Custom Tab via `OfuseLauncher`.

If you want to trigger the link from your own custom UI instead of the
built-in row, call the launcher directly:

```kotlin
OfuseLauncher.openSupportLink(context, "https://ko-fi.com/yourhandle")
```

## Design notes

- **Ko-fi first, but not hardcoded.** `supportUrl` is just a string — point
  it at Ko-fi, Buy Me a Coffee, GitHub Sponsors, or anywhere else.
- **No Play Billing.** This intentionally never touches in-app purchases;
  it's a plain external link, matching how most indie Android apps handle
  no-strings donation links.
- minSdk 31. Requires `androidx.browser` (Custom Tabs) and Compose Material3
  in the consuming app (pulled in transitively via `api(...)` in this
  library's dependencies).

## Sample app

The `:sample` module in this repo demonstrates integration end-to-end —
run it directly from Android Studio to see `SupportDeveloperRow` in context.
