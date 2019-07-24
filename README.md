# Bamboo OverOps Quality Gates Plugin

## Build steps

 ##### 1. Install Atlassian SDK
 * Mac OS (HomeBrew)
    1. Open a Terminal window and add the Atlassian "Tap" to your Brew using the command:
    <br />`brew tap atlassian/tap`
    2. Then install the SDK using the atlassian/tap command:
    <br />`brew install atlassian/tap/atlassian-plugin-sdk`
    3. [Next: Verify that you have set up the SDK correctly](https://developer.atlassian.com/display/DOCS/Install+the+Atlassian+SDK+on+a+Linux+or+Mac+system#InstalltheAtlassianSDKonaLinuxorMacsystem-step3Step3:VerifythatyouhavesetuptheSDKcorrectly)
 
 * Debian, Ubuntu Linux
    1. First, set up the Atlassian SDK repositories:
    <br /> `sudo sh -c 'echo "deb https://packages.atlassian.com/debian/atlassian-sdk-deb/ stable contrib" >>/etc/apt/sources.list'`
    2. Download the public key using curl or wget:
    <br /> `wget https://packages.atlassian.com/api/gpg/key/public`
    3. Add the public key to apt to verify the package signatures automatically:
    <br /> `sudo apt-key add public   ` 
    4. Then, run the install:
    <br /> `sudo apt-get update`
    <br /> `sudo apt-get install atlassian-plugin-sdk`
    6. [Next: Verify that you have set up the SDK correctly](https://developer.atlassian.com/display/DOCS/Install+the+Atlassian+SDK+on+a+Linux+or+Mac+system#InstalltheAtlassianSDKonaLinuxorMacsystem-step3Step3:VerifythatyouhavesetuptheSDKcorrectly)
    
 * Red Hat Enterprise Linux, CentOS, Fedora (RPM)
    
    To install on systems that use the Yum package manager:
    
    1. Create the repo file in your /etc/yum.repos.d/ folder:
    <br />`sudo vi /etc/yum.repos.d/artifactory.repo`
    2. Configure the repository details:
    <br /> `[Artifactory]`
    <br /> `name=Artifactory`
    <br /> `baseurl=https://packages.atlassian.com/yum/atlassian-sdk-rpm/`
    <br /> `enabled=1`
    <br /> `gpgcheck=0`
    3. Install the SDK:
    <br /> `sudo yum clean all`
    <br /> `sudo yum updateinfo metadata`
    <br /> `sudo yum install atlassian-plugin-sdk`
    4. [Next: Verify that you have set up the SDK correctly](https://developer.atlassian.com/display/DOCS/Install+the+Atlassian+SDK+on+a+Linux+or+Mac+system#InstalltheAtlassianSDKonaLinuxorMacsystem-step3Step3:VerifythatyouhavesetuptheSDKcorrectly)
    
 * .tgz File
    1. [Download a TGZ (GZipped tar file) of the SDK](https://marketplace.atlassian.com/download/plugins/atlassian-plugin-sdk-tgz?_ga=2.135130865.1342090517.1563909899-484610990.1554922063)
    2. Locate the downloaded SDK file. 
    3. Extract the file to your local directory. 
    <br /> `sudo tar -xvzf atlassian-plugin-sdk-4.0.tar.gz -C /opt` 
    4. Rename the extracted folder to  atlassian-plugin-sdk .
    <br /> `sudo mv /opt/atlassian-plugin-sdk-4.0 /opt/atlassian-plugin-sdk `
    5. [Next: Verify that you have set up the SDK correctly](https://developer.atlassian.com/display/DOCS/Install+the+Atlassian+SDK+on+a+Linux+or+Mac+system#InstalltheAtlassianSDKonaLinuxorMacsystem-step3Step3:VerifythatyouhavesetuptheSDKcorrectly)
    
 ##### 2. Verify that you have set up the SDK correctly
 * Open a terminal window and run the following command:
 <br /> `atlas-version`
 <br /> `ATLAS Version:    6.2.9`
 <br /> `ATLAS Home:       /usr/local/Cellar/atlassian-plugin-sdk/6.2.4/libexec`
 <br /> `ATLAS Scripts:    /usr/local/Cellar/atlassian-plugin-sdk/6.2.4/libexec/bin`
 <br /> `ATLAS Maven Home: /usr/local/Cellar/atlassian-plugin-sdk/6.2.4/libexec/apache-maven-3.2.1`
 <br /> `AMPS Version:     6.2.6`
 <br /> `--------`
 <br /> `Executing: /usr/local/Cellar/atlassian-plugin-sdk/6.2.4/libexec/apache-maven-3.2.1/bin/mvn --version -gs /usr/local/Cellar/atlassian-plugin-sdk/6.2.4/libexec/apache-maven-3.2.1/conf/settings.xml`
 <br /> `Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=256M; support was removed in 8.0`
 <br /> `Apache Maven 3.2.1 (ea8b2b07643dbb1b84b6d16e1f08391b666bc1e9; 2014-02-15T04:37:52+10:00)`
 <br /> `Maven home: /usr/local/Cellar/atlassian-plugin-sdk/6.2.4/libexec/apache-maven-3.2.1`
 <br /> `Java version: 1.8.0_45, vendor: Oracle Corporation`
 <br /> `Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home/jre`
 <br /> `Default locale: en_US, platform encoding: UTF-8`
 <br /> `OS name: "mac os x", version: "10.11.6", arch: "x86_64", family: "mac"`
 
 ##### 3. NOTICE
 * in the future use [`atlas-mvn`](https://developer.atlassian.com/server/framework/atlassian-sdk/atlas-mvn/) instead of `mvn`
 <br /> `atlas-mvn [options]`
 <br /> `atlas-mvn -help`
 <br /> `atlas-mvn clean package`
    

