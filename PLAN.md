# Ofuse — Next Steps Plan

Context for whoever picks this up: this is a small, published Android
library (JitPack `com.github.AlienAsRoger:ofuse:0.1.2`), not an app — scope
accordingly, it should stay minimal. Read `CLAUDE.md` first for the full
design rationale and hard-won JitPack/compileSdk/Gradle-wrapper gotchas.

> **Convention:** as work on an item below is completed, check it off
> (`- [x]`) and add a one-line note of what changed and when, instead of
> deleting it — that's what lets a fresh session pick this up without
> re-deriving context.

## Where things stand

- `:ofuse` library module: `SupportDeveloperRow` (Material3 `ListItem`
  composable) + `OfuseLauncher` (opens a URL in a Custom Tab). Published and
  working on JitPack.
- Two real consumers: `Makoto` and `MovieSuggestion`, both pointing at Ko-fi.
- `:sample` module demonstrates integration end-to-end.
- compileSdk 36 / AGP 8.13.2 / Gradle wrapper 8.13 — deliberately kept on
  stable, non-alpha versions so the published AAR stays consumable by apps
  on ordinary stable tooling (see `CLAUDE.md` for the 0.1.0/0.1.1 breakage
  this fixed).

## Immediate gaps

- [x] **LICENSE file** — added; repo is public and consumable via JitPack.
- [ ] **No automated tests** ([#1](https://github.com/AlienAsRoger/Ofuse/issues/1)) — unit or instrumented — beyond the default
      `:sample` template stubs.
- [ ] **No CI (GitHub Actions).** ([#2](https://github.com/AlienAsRoger/Ofuse/issues/2)) JitPack's own build is currently the only
      thing verifying `:ofuse` compiles on a clean checkout. Worth adding a
      basic build/lint workflow so breakage is caught before a version tag
      is pushed, not discovered on JitPack (see the 0.1.1 incident in
      `CLAUDE.md`).

## Ideas worth prototyping next

- [ ] **A second donation platform / deep-link support.** ([#3](https://github.com/AlienAsRoger/Ofuse/issues/3)) Currently
      deliberately just a plain `String` URL (Ko-fi-shaped, but
      platform-agnostic). Only worth a `SupportPlatform` abstraction if a
      real second platform needs different linking behavior — don't build
      this speculatively.
- [ ] **A third consumer app.** ([#4](https://github.com/AlienAsRoger/Ofuse/issues/4)) `Kodama` and `YourLeetCode` don't currently
      depend on Ofuse — worth checking whether they should, for consistency
      with `Makoto`/`MovieSuggestion`.
- [ ] **Pre-release JitPack build verification.** ([#5](https://github.com/AlienAsRoger/Ofuse/issues/5)) Given the past incident
      where a locally-fine build failed on JitPack's clean checkout, worth
      scripting a local "clean build" check (delete `.gradle`/`build`,
      rebuild cold) as a pre-tag step, whether or not full CI lands first.

## Useful facts for a fresh session / different tool

- Remote: `https://github.com/AlienAsRoger/Ofuse.git`, branch `main`.
- Package `com.developer4droid.ofuse`, minSdk 31.
- Versioning: bump the version in `ofuse/build.gradle.kts`'s `maven-publish`
  block, tag, push — JitPack builds on-demand from the tag. A version that
  fails JitPack's build should be deleted/retagged rather than left as a
  permanently-broken release (see the 0.1.1 precedent).
