# run.avelino/logseq-libs

`[@logseq/libs](https://logseq.github.io/plugins/)` **wrapper** for *ClojureScript*, making it friendly to work with Logseq Plugin API in ClojureScript.

## Status

✅ All core modules have been implemented:

### Core API

- `ready!`: Initialize plugin and register model
- `provide-model!`: Provide a model object to Logseq
- `provide-style!`: Provide CSS styles to Logseq
- `provide-ui!`: Provide UI elements to Logseq
- `update-settings!`: Update plugin settings
- `on-settings-changed!`: Register settings change handler
- `before-unload!`: Register before unload handler

### UI API

- `show-msg!`: Show a notification message
- `close-msg!`: Close a notification message
- `query-element-rect!`: Query element rectangle by selector
- `check-slot-valid!`: Check if a UI slot is valid

### Editor API

- Block Operations
  - `remove-block!`: Remove a block by its UUID or reference
  - `insert-block!`: Insert a new block
  - `update-block!`: Update block content
  - `get-block!`: Get block by UUID or reference
  - `edit-block!`: Edit block
  - `select-block!`: Select block
- Page Operations
  - `get-page!`: Get page by name
  - `create-page!`: Create a new page
  - `delete-page!`: Delete a page by name
  - `rename-page!`: Rename a page
  - `get-all-pages!`: Get all pages
  - `get-pages-tree-data!`: Get pages tree data
- Editing State
  - `check-editing!`: Check if in editing mode
  - `exit-editing-mode!`: Exit editing mode
  - `restore-editing-cursor!`: Restore editing cursor
  - `get-editing-cursor-position!`: Get editing cursor position
  - `get-editing-content-slate-value!`: Get editing content slate value
- Current State
  - `get-current-page!`: Get current page
  - `get-current-block!`: Get current block
  - `get-selected-blocks!`: Get selected blocks
- Navigation
  - `open-in-right-sidebar!`: Open block or page in right sidebar
  - `scroll-to-block-in-page!`: Scroll to block in page
- Events
  - `on-input-selection-end!`: Register input selection end handler

### DB API

- Query Operations
  - `q!`: Run a DSL query
  - `datascript-query!`: Run a datascript query
- Change Subscriptions
  - `on-changed!`: Subscribe to DB changes
  - `on-block-changed!`: Subscribe to specific block changes

### Git API

- `exec-command!`: Execute a git command
- `load-ignore-file!`: Load git ignore file
- `save-ignore-file!`: Save git ignore file

### Assets API

- `list-files-of-current-graph!`: List files of current graph
- `make-sandbox-storage!`: Make sandbox storage
- `make-url!`: Make assets scheme url based on current graph
- `built-in-open!`: Try to open asset type file in Logseq app

### Theme API

- `register-theme!`: Register a new theme
- `unregister-theme!`: Unregister a theme
- `select-theme!`: Select a theme

Want to help? Check the [Contributing](#contributing) section!

## Features

- 🚀 Full ClojureScript support for Logseq Plugin API
- 💪 Idiomatic Clojure interface (functions, not class/methods)
- 🧩 Modular design with separate namespaces
- 📚 Comprehensive documentation and examples

## Installation

Add to your `deps.edn`:

```clojure
{:deps {run.avelino/logseq-libs {:mvn/version "0.1.0"}}}
```

That's it! The `@logseq/libs` npm package is included as a dependency and will be installed automatically.

## Quick Start

```clojure
(ns my-plugin.core
  (:require [run.avelino.logseq-libs.core :as ls]
            [run.avelino.logseq-libs.ui :as ls-ui]))

(ls/ready!
  (fn []
    (ls-ui/show-msg! "Plugin ready!")))
```

## Development

### Prerequisites

- [Babashka](https://github.com/babashka/babashka#installation)
- [Node.js](https://nodejs.org/) (for npm/shadow-cljs)

### Setup

1. Clone the repository

```bash
git clone https://github.com/avelino/logseq-libs.git
cd logseq-libs
```

2. Install dependencies

```bash
npm install
```

### Development Commands

All commands are run through Babashka tasks:

```bash
# Clean build artifacts
bb clean

# Watch for changes and recompile
bb watch

# Create production build
bb release
```

## Example Plugin

Check the [example](./example) directory for a complete working plugin using this library.

To run the example:

```bash
cd example
bb watch
```

## Project Structure

```
logseq-cljs-lib/
├── deps.edn           # Project dependencies
├── bb.edn            # Babashka tasks
├── src/              # Source files
│   └── run/
│       └── avelino/
│           └── logseq_libs/
│               ├── core.cljs    # Core functions
│               ├── ui.cljs      # UI functions
│               ├── editor.cljs  # Editor functions
│               └── ...
└── example/          # Example plugin
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines

- Follow Clojure style guide
- Add tests for new features
- Update documentation when needed
- Keep commits clean and well-documented
- Keep it simple - we are working on a wrapper for an existing library

## Available Modules

- `core`: Core plugin functionality (ready!, provide-model!, etc)
- `ui`: UI related functions (show-msg!, etc)
- `editor`: Editor related functions
- `db`: Database/Query related functions
- `assets`: Assets related functions
- `git`: Git related functions
- `theme`: Theme related functions

## License

MIT License - see [LICENSE](LICENSE) for details

## Links

- [Logseq Plugin API Documentation](https://logseq.github.io/plugins/)
- [Logseq Plugin Examples](https://github.com/logseq/logseq-plugin-samples)
- [Original @logseq/libs source](https://github.com/logseq/logseq/tree/master/libs)
