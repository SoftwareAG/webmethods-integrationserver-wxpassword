# WxPassword

Initial functionality is to change the passwords of the built-in users of
webMethods Integration Server
(Administrator, Replicator, Developer). The primary use-case is deployment 
in a container.

## Usage

To enable the automated update of passwords the environment variable
`SAG_WXPASSWORD_UPDATE_DEFAULT_ACCOUNTS` must be set to `true`.
This mechanism protects against accidental execution and by that
a potential lock-out.


### Specific passwords

In a typical production deployment, the desired passwords need to
be provide as environment variables. The naming convention is
`SAG_WXPASSWORD_SET_<USERNAME>`. So for the `Administrator` account 
the variable `SAG_WXPASSWORD_SET_Administrator` must be defined.

### Random passwords

If no password is defined via environment variable, a random one will 
be generated and saved in clear text in the working directory. For 
each user a separate file will be created and its name matches the user name.

**Intended only for
non-production environments!**

### Working directory

The working directory needs to be provided as an environment variable
(`SAG_WXPASSWORD_DIR`). If nothing is specified, the fall-back value
is `$IS_HOME/config/WxPassword`. In both cases, the directory will
be created, if it does not exist. If the creation fails, a
`ServiceException` is thrown and now further activities performed.

## Getting Started

You can use this package in multiple ways.

1. Simply make use of the existing functionality
1. Use it as a source of inspiration for your own development
1. Contribute to WxPassword by adding new functionality and/or fixing bugs

### Installing a Release

This is the suitable approach, if you either just want to use WxPassword or use it as a starting point for
your own development.

* Download `WxPassword.zip` from the [latest release](https://github.com/SoftwareAG/webmethods-integrationserver-wxpassword/releases), place it into `$IS_HOME/replicate/inbound/`, and install via "Packages / Management / Install Inbound Releases"
  
### Installation from Source

- Prerequisite: You need "Local Service Development" installed (located in Designer preferences at  "Software AG / Service Development / Local Service Development")
- Get sources
  - Via Software AG Designer (no separate Git installation needed)
    - Open "Java" perspective
	- Click "Import projects"
	- Select "Git / Projects from Git"
	- Select "Clone URI"
	- Paste Git URI from green "Clone or download" button above
	- Adjust target directory to `<WORKSPACE>/WxPassword`
	- Confirm defaults on all further dialogues and finish the import
  - Via command line (requires local Git installation)
    - Go into Designer workspace (e.g. `/home/john/workspace105`)
    - Clone Git repository into new directory `git clone https://github.com/SoftwareAG/webmethods-integrationserver-wxpassword.git WxPassword`
	- Import as existing project into workspace
- Activate package in Integration Server
	- If the "Service Development" perspective has not been active before you openend the "Java" perspective, you must quickly switch there and then directly back to "Java". This is needed to initialize the Local Service Development feature.
	- In the "Java" perspective right-click the project name and select "Move Project to IS Package"
	- Switch to the "Service Development" perspective and the WxPassword package should show up
- Compile and frag package (required because for Java services neither the class files nor the frag files are versioned)
  - Open Command Promopt (`cmd.exe`) and go into `$IS_HOME/bin`	directory
  - Execute the following commands
    ```
    jcode makeall WxPassword
    jcode fragall WxPassword
    ```


------------------------------

These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.
