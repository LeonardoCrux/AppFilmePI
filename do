[33mcommit da561e03480cac6acbb062f358ad8e919f76f327[m[33m ([m[1;36mHEAD -> [m[1;32mmaster[m[33m)[m
Author: LeonardoCrux <leo.acruz@hotmail.com>
Date:   Mon Apr 13 20:06:31 2020 -0300

    commit 1

[1mdiff --git a/.gitignore b/.gitignore[m
[1mnew file mode 100644[m
[1mindex 0000000..603b140[m
[1m--- /dev/null[m
[1m+++ b/.gitignore[m
[36m@@ -0,0 +1,14 @@[m
[32m+[m[32m*.iml[m
[32m+[m[32m.gradle[m
[32m+[m[32m/local.properties[m
[32m+[m[32m/.idea/caches[m
[32m+[m[32m/.idea/libraries[m
[32m+[m[32m/.idea/modules.xml[m
[32m+[m[32m/.idea/workspace.xml[m
[32m+[m[32m/.idea/navEditor.xml[m
[32m+[m[32m/.idea/assetWizardSettings.xml[m
[32m+[m[32m.DS_Store[m
[32m+[m[32m/build[m
[32m+[m[32m/captures[m
[32m+[m[32m.externalNativeBuild[m
[32m+[m[32m.cxx[m
[1mdiff --git a/.idea/.name b/.idea/.name[m
[1mnew file mode 100644[m
[1mindex 0000000..7297b1f[m
[1m--- /dev/null[m
[1m+++ b/.idea/.name[m
[36m@@ -0,0 +1 @@[m
[32m+[m[32mAppFilme[m
\ No newline at end of file[m
[1mdiff --git a/.idea/codeStyles/Project.xml b/.idea/codeStyles/Project.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..681f41a[m
[1m--- /dev/null[m
[1m+++ b/.idea/codeStyles/Project.xml[m
[36m@@ -0,0 +1,116 @@[m
[32m+[m[32m<component name="ProjectCodeStyleConfiguration">[m
[32m+[m[32m  <code_scheme name="Project" version="173">[m
[32m+[m[32m    <codeStyleSettings language="XML">[m
[32m+[m[32m      <indentOptions>[m
[32m+[m[32m        <option name="CONTINUATION_INDENT_SIZE" value="4" />[m
[32m+[m[32m      </indentOptions>[m
[32m+[m[32m      <arrangement>[m
[32m+[m[32m        <rules>[m
[32m+[m[32m          <section>[m
[32m+[m[32m            <rule>[m
[32m+[m[32m              <match>[m
[32m+[m[32m                <AND>[m
[32m+[m[32m                  <NAME>xmlns:android</NAME>[m
[32m+[m[32m                  <XML_ATTRIBUTE />[m
[32m+[m[32m                  <XML_NAMESPACE>^$</XML_NAMESPACE>[m
[32m+[m[32m                </AND>[m
[32m+[m[32m              </match>[m
[32m+[m[32m            </rule>[m
[32m+[m[32m          </section>[m
[32m+[m[32m          <section>[m
[32m+[m[32m            <rule>[m
[32m+[m[32m              <match>[m
[32m+[m[32m                <AND>[m
[32m+[m[32m                  <NAME>xmlns:.*</NAME>[m
[32m+[m[32m                  <XML_ATTRIBUTE />[m
[32m+[m[32m                  <XML_NAMESPACE>^$</XML_NAMESPACE>[m
[32m+[m[32m                </AND>[m
[32m+[m[32m              </match>[m
[32m+[m[32m              <order>BY_NAME</order>[m
[32m+[m[32m            </rule>[m
[32m+[m[32m          </section>[m
[32m+[m[32m          <section>[m
[32m+[m[32m            <rule>[m
[32m+[m[32m              <match>[m
[32m+[m[32m                <AND>[m
[32m+[m[32m                  <NAME>.*:id</NAME>[m
[32m+[m[32m                  <XML_ATTRIBUTE />[m
[32m+[m[32m                  <XML_NAMESPACE>http://schemas.android.com/apk/res/android</XML_NAMESPACE>[m
[32m+[m[32m                </AND>[m
[32m+[m[32m              </match>[m
[32m+[m[32m            </rule>[m
[32m+[m[32m          </section>[m
[32m+[m[32m          <section>[m
[32m+[m[32m            <rule>[m
[32m+[m[32m              <match>[m
[32m+[m[32m                <AND>[m
[32m+[m[32m                  <NAME>.*:name</NAME>[m
[32m+[m[32m                  <XML_ATTRIBUTE />[m
[32m+[m[32m                  <XML_NAMESPACE>http://schemas.android.com/apk/res/android</XML_NAMESPACE>[m
[32m+[m[32m                </AND>[m
[32m+[m[32m              </match>[m
[32m+[m[32m            </rule>[m
[32m+[m[32m          </section>[m
[32m+[m[32m          <section>[m
[32m+[m[32m            <rule>[m
[32m+[m[32m              <match>[m
[32m+[m[32m                <AND>[m
[32m+[m[32m                  <NAME>name</NAME>[m
[32m+[m[32m                  <XML_ATTRIBUTE />[m
[32m+[m[32m                  <XML_NAMESPACE>^$</XML_NAMESPACE>[m
[32m+[m[32m                </AND>[m
[32m+[m[32m              </match>[m
[32m+[m[32m            </rule>[m
[32m+[m[32m          </section>[m
[32m+[m[32m          <section>[m
[32m+[m[32m            <rule>[m
[32m+[m[32m              <match>[m
[32m+[m[32m                <AND>[m
[32m+[m[32m                  <NAME>style</NAME>[m
[32m+[m[32m                  <XML_ATTRIBUTE />[m
[32m+[m[32m                  <XML_NAMESPACE>^$</XML_NAMESPACE>[m
[32m+[m[32m                </AND>[m
[32m+[m[32m              </match>[m
[32m+[m[32m            </rule>[m
[32m+[m[32m          </section>[m
[32m+[m[32m          <section>[m
[32m+[m[32m            <rule>[m
[32m+[m[32m              <match>[m
[32m+[m[32m                <AND>[m
[32m+[m[32m                  <NAME>.*</NAME>[m
[32m+[m[32m                  <XML_ATTRIBUTE />[m
[32m+[m[32m                  <XML_NAMESPACE>^$</XML_NAMESPACE>[m
[32m+[m[32m                </AND>[m
[32m+[m[32m              </match>[m
[32m+[m[32m              <order>BY_NAME</order>[m
[32m+[m[32m            </rule>[m
[32m+[m[32m          </section>[m
[32m+[m[32m          <section>[m
[32m+[m[32m            <rule>[m
[32m+[m[32m              <match>[m
[32m+[m[32m                <AND>[m
[32m+[m[32m                  <NAME>.*</NAME>[m
[32m+[m[32m                  <XML_ATTRIBUTE />[m
[32m+[m[32m                  <XML_NAMESPACE>http://schemas.android.com/apk/res/android</XML_NAMESPACE>[m
[32m+[m[32m                </AND>[m
[32m+[m[32m              </match>[m
[32m+[m[32m              <order>ANDROID_ATTRIBUTE_ORDER</order>[m
[32m+[m[32m            </rule>[m
[32m+[m[32m          </section>[m
[32m+[m[32m          <section>[m
[32m+[m[32m            <rule>[m
[32m+[m[32m              <match>[m
[32m+[m[32m                <AND>[m
[32m+[m[32m                  <NAME>.*</NAME>[m
[32m+[m[32m                  <XML_ATTRIBUTE />[m
[32m+[m[32m                  <XML_NAMESPACE>.*</XML_NAMESPACE>[m
[32m+[m[32m                </AND>[m
[32m+[m[32m              </match>[m
[32m+[m[32m              <order>BY_NAME</order>[m
[32m+[m[32m            </rule>[m
[32m+[m[32m          </section>[m
[32m+[m[32m        </rules>[m
[32m+[m[32m      </arrangement>[m
[32m+[m[32m    </codeStyleSettings>[m
[32m+[m[32m  </code_scheme>[m
[32m+[m[32m</component>[m
\ No newline at end of file[m
[1mdiff --git a/.idea/gradle.xml b/.idea/gradle.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..5cd135a[m
[1m--- /dev/null[m
[1m+++ b/.idea/gradle.xml[m
[36m@@ -0,0 +1,20 @@[m
[32m+[m[32m<?xml version="1.0" encoding="UTF-8"?>[m
[32m+[m[32m<project version="4">[m
[32m+[m[32m  <component name="GradleMigrationSettings" migrationVersion="1" />[m
[32m+[m[32m  <component name="GradleSettings">[m
[32m+[m[32m    <option name="linkedExternalProjectsSettings">[m
[32m+[m[32m      <GradleProjectSettings>[m
[32m+[m[32m        <option name="testRunner" value="PLATFORM" />[m
[32m+[m[32m        <option name="distributionType" value="DEFAULT_WRAPPED" />[m
[32m+[m[32m        <option name="externalProjectPath" value="$PROJECT_DIR$" />[m
[32m+[m[32m        <option name="modules">[m
[32m+[m[32m          <set>[m
[32m+[m[32m            <option value="$PROJECT_DIR$" />[m
[32m+[m[32m            <option value="$PROJECT_DIR$/app" />[m
[32m+[m[32m          </set>[m
[32m+[m[32m        </option>[m
[32m+[m[32m        <option name="resolveModulePerSourceSet" value="false" />[m
[32m+[m[32m      </GradleProjectSettings>[m
[32m+[m[32m    </option>[m
[32m+[m[32m  </component>[m
[32m+[m[32m</project>[m
\ No newline at end of file[m
[1mdiff --git a/.idea/misc.xml b/.idea/misc.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..7bfef59[m
[1m--- /dev/null[m
[1m+++ b/.idea/misc.xml[m
[36m@@ -0,0 +1,9 @@[m
[32m+[m[32m<?xml version="1.0" encoding="UTF-8"?>[m
[32m+[m[32m<project version="4">[m
[32m+[m