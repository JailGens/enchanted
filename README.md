# Enchanted

Enchanted is a lightweight, annotation-driven command library.

## Motivation

Many other command frameworks are too complex and struggle to model different types of commands.

Another thing is that they often attempt to handle tasks that should be handled by a different
framework, such as dependency injection, and internationalisation. Although we do support
internationalisation, the implementation is up to the end user (we still offer a reasonable default
implementation).

One thing you will notice when using these frameworks is the lack of support for overloaded
commands (commands with the same name but different parameters). Enchanted has full support for
this with no compromises.

My final issue with some of these other frameworks is that they delay command validation (verify
overloads, command names, etc.) until runtime. This
[blog](https://blog.softwaremill.com/the-case-against-annotations-4b2fb170ed67) talks about it a bit
more in-depth.

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
