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

This repo is a **freshly scaffolded default Android Studio project** — single
`:app` module, Jetpack Compose, minSdk 31 / targetSdk 37, package
`com.developer4droid.ofuse`. Nothing library-specific has been built yet:

- No separate library module exists yet. The existing `:app` module is
  template boilerplate (default `MainActivity` + theme files) and is intended
  to become (or be replaced by) the **sample/test app** that exercises the
  library — this needs a production SDK, not just inline code living in a
  single app, so the repo should end up with at least two modules: a
  publishable library module (e.g. `:ofuse` or `:library`) and a `:sample` (or
  renamed `:app`) module that depends on it and demonstrates real usage.
- No donation-link UI, no Ko-fi integration, no publishing setup (Maven
  coordinates, JitPack/GitHub Packages, versioning) exists yet.

## Intended design (discussed, not yet built)

- **Ko-fi first.** Ko-fi was chosen over Buy Me a Coffee (5% platform fee) and
  GitHub Sponsors (developer-coded audience, less recognizable to general
  end-users) because it has a 0% platform fee and reads as a generic
  "support this creator" link to non-technical users — which matters since
  consuming apps like Makoto are aimed at general users, not developers.
- **External link, not in-app purchase.** The common, Play-policy-safe pattern
  in indie Android apps is a "Support development" row (e.g. in a Settings
  screen) that opens the creator's Ko-fi page via a Custom Tab — no Play
  Billing integration, since a no-strings-attached tip isn't a digital good
  purchased inside the app. Ofuse should follow that pattern rather than
  building any in-app purchase flow.
- **Keep the platform swappable.** Even though Ko-fi is the first (and only
  initially required) backend, avoid hardcoding "Ko-fi" throughout the public
  API — a consuming app should configure a URL/handle, and the library's
  surface should be able to accommodate a different or additional platform
  later (Buy Me a Coffee, GitHub Sponsors) without a breaking API change, in
  the same spirit as `BubbleHost` in Makoto being an interface that
  `NotificationBubbleHost` implements. Don't over-build this abstraction
  before there's a second real platform to support, though — a single
  configurable "support link" entry point is enough for v1.
- **Production SDK bar.** This isn't a copy-pasted snippet — it should have
  its own sample app demonstrating integration, sensible Compose theming that
  doesn't fight a consumer app's own theme, and be set up for actual
  publishing (Maven coordinates + a real distribution mechanism) so other
  projects can add it as a normal Gradle dependency.

## Relationship to other projects

- `Makoto` (`/Users/alekseishchekin/StudioProjects/Makoto`) is the first
  intended consumer — it currently has no donation mechanism; once Ofuse has
  a usable first version, Makoto should add it as a dependency and surface a
  "Support development" entry point (likely from `MainActivity`, alongside
  its existing "Real trigger" / "Manual demo" sections).
- More apps will likely consume this over time — that's the whole reason this
  is its own repo/library instead of code living inside Makoto.

## Next steps (not yet built)

1. Add a real library module (decide `:ofuse` vs `:library` naming) separate
   from the sample app module.
2. Design the minimal public API: something like a single Compose
   composable/button (or settings-row component) configured with a Ko-fi
   page URL, opening it via `CustomTabsIntent`.
3. Turn the existing `:app` module into (or add) a `:sample` module that
   depends on the library module and demonstrates real integration.
4. Decide on and set up distribution (JitPack from this GitHub repo is the
   lowest-ceremony option; local Maven or GitHub Packages are alternatives)
   so Makoto and future apps can depend on a published version rather than a
   local path/module include.
5. Integrate into Makoto once a first version exists.
