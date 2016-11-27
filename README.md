# Crypto Playground

This is a sandbox repo for experimenting with various crypto cyphers for use in mobile messaging apps. (eg: [where@](https://whereat.io)

# To edit/run/test app

* import project into IntelliJ using gradle

* to download any missing dependencies:
  * run `gradle build` from CLI
  * or.. execute `Synchronize` command in IntelliJ (`C-M-Y` in emacs keybinding)

* to run app: 
  * run `gradle run` from CLI
  * or... execute `Run` command in IntelliJ (`C-Shift-F10` in emacs keybinding)from `com.example.App`, will bring up CLI

* to run tests:
  * run `gradle test` from CLI
  * or... execute `Run All Tests` command in IntelliJ (`C-Shift-F10` while selecting `src/test` in Project Pane)

# TODO

* Add asymmetric cypher
* Add signatures
* Add signature verification
* Add simulated routing (a la propose where@ architecture)
* Add Signal protocol simulation (whee!!!)