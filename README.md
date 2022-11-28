# Enchanted

Enchanted is a lightweight, annotation-driven command library.

## Motivation

Many other command frameworks are too complex and support features that aren't really their job such
as complete internationalisation APIs, dependency injection, and configuration.

Another issue with almost all other command frameworks is that they delay command validation (verify
overloads, command names, etc.) until runtime. Enchanted can validate all of this and compile-time 
thanks to its annotation processor.

## Features

| Feature              | Enchanted |
|----------------------|-----------|
| Internationalisation | ❌         |
| Annotation Processor | ❌         |
| Command Overloading  | ❌         |
| Suggestions          | ❌         |
| Aliases              | ❌         |
| Validation           | ❌         |
| Service Loader       | ❌         |
| Usage Generation     | ❌         |
| Paper                | ❌         |
| Discord4J            | ❌         |

## Credit

- This project was inspired by [ACF](https://github.com/aikar/commands)
- Credit to [triumph-cmds](https://triumphteam.dev/library/triumph-cmds/introduction) for the idea
  of `@Join`
