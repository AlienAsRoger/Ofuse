# Ofuse

A reusable Android library ("SDK") that gives any app a simple, drop-in
"support the developer" entry point — a donation/tip link, initially backed by
Ko-fi. Built to be shared as a Gradle dependency across multiple of the
author's Android apps (the first consumer is `Makoto`, a separate project at
`/Users/alekseishchekin/StudioProjects/Makoto`), rather than re-implementing
the same "support this app" button in every project.

## Naming

Named お布施 (*ofuse*) — the Japanese word for a monetary offering/tip, already
used colloquially in Japanese creator culture (there's a real platform called
OFUSE where fans tip illustrators and writers). Chosen deliberately in the
style of Dagger → Hilt: a short, real-word name for a concrete concept, not an
invented portmanteau. It also pairs thematically with `Makoto` (真, "truth/
sincerity") — both are genuine Japanese words rather than corporate-sounding
brand names.

Package namespace: `com.developer4droid.ofuse`.

## Current state (as of 2026-07-10)

Built and published:

- `:ofuse` — the library module (`com.android.library`), containing:
  - `SupportDeveloperRow` (`SupportDeveloperRow.kt`) — a ready-made
    Material3 `ListItem` composable, configured with a `supportUrl` string,
    optional title/description/icon.
  - `OfuseLauncher` (`OfuseLauncher.kt`) — a standalone `object` that opens a
    URL in a Custom Tab (`androidx.browser`), used internally by
    `SupportDeveloperRow` and also exposed for consumers who want to trigger
    it from their own UI.
  - Published on JitPack as `com.github.AlienAsRoger:ofuse:0.1.1` (repo is
    public; JitPack's free tier requires that). Versioning/publishing is via
    a `maven-publish` block in `ofuse/build.gradle.kts` with explicit
    `groupId`/`artifactId` overrides, plus a root `jitpack.yml` pinning
    `openjdk21`.
  - **compileSdk 36, not 37** (downgraded from the original 0.1.0, along with
    AGP 9.4.0-alpha01 → 8.13.2, `androidx.core-ktx` 1.19.0 → 1.17.0,
    `composeBom` → 2025.12.01, `lifecycle-runtime-ktx` → 2.10.0). 0.1.0 was
    accidentally built against compileSdk 37 / an alpha AGP, which baked a
    compileSdk-37 requirement into the published AAR — any consumer still on
    stable AGP 8.x / compileSdk 36 (like `MovieSuggestion`, see below)
    couldn't add the dependency at all. `Makoto` was already on compileSdk 37
    so it never hit this; keep new versions on the lowest compileSdk that
    still builds, not whatever the local IDE template defaults to, so the
    library stays consumable by apps on stable tooling.
- `:sample` — a small app module depending on `:ofuse`, demonstrating real
  integration (run it directly from Android Studio).
- `README.md` — installation (JitPack repo + dependency coordinates) and
  usage instructions, written so this can be handed to a fresh Claude Code
  session in any consuming project without re-deriving the setup.
- Consumers:
  - `Makoto` (`app/build.gradle.kts` + `MainActivity.kt`), linking to
    `https://ko-fi.com/developer4droid`, verified end-to-end on-device.
  - `MovieSuggestion` (`app/build.gradle.kts` +
    `ui/screens/SubscriptionPickerScreen.kt`), same Ko-fi URL, added on
    2026-07-10 — the app that surfaced the compileSdk-37 problem above.

## Design decisions (why it's built this way)

- **Ko-fi first, not hardcoded.** Ko-fi was chosen over Buy Me a Coffee (5%
  platform fee) and GitHub Sponsors (developer-coded audience, less
  recognizable to general end-users) for its 0% platform fee and generic
  "support this creator" framing — but `SupportDeveloperRow.supportUrl` is
  just a `String`, so it works with any platform's URL without library
  changes. Don't build a `SupportPlatform` enum/abstraction unless a second
  real platform actually needs different handling (e.g. platform-specific
  deep links) — a single configurable URL is enough for v1.
- **External link, not in-app purchase.** `OfuseLauncher` only ever opens a
  Custom Tab — no Play Billing integration. This matches the common,
  Play-policy-safe pattern in indie Android apps for no-strings-attached
  tips, and deliberately avoids the complexity (and Google's cut) of a real
  IAP flow for something that isn't a digital good purchased in-app.
- **`api(...)` for Compose/Material3 in `ofuse/build.gradle.kts`.** The
  library's public surface returns Composable functions using Material3
  types, so those dependencies are exposed transitively rather than as
  `implementation`, avoiding duplicate/mismatched Compose versions in
  consumers.

## Relationship to other projects

- `Makoto` is the first consumer and already depends on the published
  JitPack artifact (not a local module include) — treat this repo and
  Makoto as fully independent from here on; changes to Ofuse need a version
  bump + new JitPack build before Makoto (or any other consumer) picks them
  up.
- More apps may consume this over time — that's the reason this is its own
  repo/library instead of code living inside Makoto.

## Next steps (not yet built)

- No `LICENSE` file yet — needed now that the repo is public and consumable
  by other projects via JitPack.
- No automated tests (unit or instrumented) beyond the default template
  stubs in `:sample`.
- No CI (GitHub Actions) — JitPack's own build is the only thing currently
  verifying `:ofuse` compiles.
- If a second donation platform is ever added with different linking needs
  (e.g. an SDK instead of a plain URL), revisit the "keep it a plain
  `String`" decision above — don't preemptively build for that now.
