# Contributing to Enchanted

When contributing to Enchanted, we require that you follow these guidelines:

1. [Bug Reports](#bug-reports)
2. [Features Requests](#features)
3. [Commit Guidelines](#commit-guidelines)
4. [Pull Request Guidelines](#pull-request-guidelines)
5. [Style](#style)

## Bug Reports

If you have found a bug, please submit an issue or pull request with the fix.

## Features

To request a feature, please submit an issue where it can be discussed. If you would like to
implement the add

## Commit Guidelines

Commits don't have to follow these guidelines, but PR titles must, so we know what you've
changed.

[Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)

- Commits should be all lowercase unless referencing a something that has casing
- Commits should be in present tense
- No full stop (.) at the end of the commit message

### Commit types

- **fix**: a non-breaking bug fix (e.g. changing a parameter name, or changing an error message) |
  patch
- **feat**: a non-breaking addition to the api (e.g. a new class, or method) | minor
- **refactor**: other code changes that affect any binaries (executable, sources, javadoc)
  (e.g. renaming a variable, or method, updating javadoc, changing implementation) | minor
- **test**: adding or updating tests | none
- **docs**: a non-breaking change to the documentation (e.g. adding a comment, or updating the
  README) | none
- **chore**: repetitive tasks (e.g. updating the gradle wrapper, or updating the version number) |
  none

## Pull Request Guidelines

Before opening a pull request, please ensure that no duplicate pull requests exist. If one does,
please comment on the existing pull request beforehand.

### Testing

Please ensure that all tests pass before opening a pull request. If you have added new
functionality, it is preferred that you add tests for it. If you have fixed a bug for a component
that is already tested, please add a test that would have caught the bug.

## Style

Please run the IntelliJ formatter before making contributions. The configuration file can be found
in the `.editorconfig` file.
