# run.avelino/logseq-libs

A ClojureScript wrapper for [`@logseq/libs`](https://logseq.github.io/plugins/), making it friendly to work with Logseq Plugin API in ClojureScript.

[![Clojars Project](https://img.shields.io/clojars/v/run.avelino/logseq-libs.svg)](https://clojars.org/run.avelino/logseq-libs)

## Quick Start

### Installation

Add to your `deps.edn`:

```clojure
{:deps {run.avelino/logseq-libs {:mvn/version "0.0.17.1"}}}
```

The `@logseq/libs` npm package is included as a dependency and will be installed automatically.

### Basic Usage

Here's a simple example of creating a Logseq plugin:

```clojure
(ns my-plugin.core
  (:require [run.avelino.logseq-libs.core :as ls]
            [run.avelino.logseq-libs.ui :as ls-ui]
            [run.avelino.logseq-libs.editor :as ls-editor]))

;; Initialize plugin
(ls/ready!
  (fn []
    (ls-ui/show-msg! "Plugin ready!")

    ;; Register a command in Logseq's command palette
    (ls/register-command-palette!
      {:key "hello-world"
       :label "Say Hello"
       :keybinding "mod+shift+h"
       :callback (fn []
                  (ls-ui/show-msg! "Hello from ClojureScript!"))})))
```

## Available Modules

The library provides several modules to interact with different aspects of Logseq:

- `core`: Plugin initialization, settings, and command registration
- `ui`: UI notifications and interactions
- `editor`: Block and page operations
- `db`: Database queries and subscriptions
- `git`: Git operations
- `assets`: File and asset management
- `theme`: Theme management

For detailed API documentation of each module, see the [API Reference](#api-reference) section below.

## API Reference

### Core API
Most commonly used functions for plugin development:

```clojure
;; Initialize plugin
(ls/ready! callback-fn)

;; Register command in command palette
(ls/register-command-palette!
  {:key "command-key"
   :label "Command Label"
   :keybinding "mod+shift+k"
   :callback fn})

;; Handle settings changes
(ls/on-settings-changed! handler-fn)
```

### Editor API
Functions for working with blocks and pages:

```clojure
;; Create a new block
(ls-editor/insert-block!
  {:page "Page Name"
   :content "Block content"})

;; Get current page
(ls-editor/get-current-page!)

;; Update block content
(ls-editor/update-block!
  block-uuid
  "New content")
```

For the complete API reference, visit our [API Documentation](https://cljdoc.org/d/run.avelino/logseq-libs/CURRENT).

## Development

### Prerequisites

- [Babashka](https://github.com/babashka/babashka#installation)
- [Node.js](https://nodejs.org/)

### Local Setup

1. Clone the repository:
```bash
git clone https://github.com/avelino/logseq-libs.git
cd logseq-libs
```

2. Install dependencies:
```bash
npm install
```

### Development Commands

```bash
# Watch for changes
bb watch

# Create production build
bb release

# Clean build artifacts
bb clean
```

## Contributing

We welcome contributions! Here's how you can help:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Run tests and ensure they pass
5. Commit your changes (`git commit -m 'Add amazing feature'`)
6. Push to your branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

### Development Guidelines

- Follow Clojure style guide
- Add tests for new features
- Update documentation
- Keep commits clean and well-documented

## License

MIT License - see [LICENSE](LICENSE) for details

## Resources

- [Logseq Plugin API Documentation](https://logseq.github.io/plugins/)
- [Logseq Plugin Examples](https://github.com/logseq/logseq-plugin-samples)
- [ClojureDocs](https://clojuredocs.org/)
