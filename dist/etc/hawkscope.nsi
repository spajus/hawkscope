;hawkscope.nsi
; ----------------------------------------
;(C) 2009 by Tomas Varaneckas
/*----------------------------------------
MANUALS
http://nsis.sourceforge.net/Docs/
http://nsis.sourceforge.net/Docs/Modern%20UI%202/Readme.html
----------------------------------------
COMMAND LINE OPTIONS (case sensitive):
/S - silent install/uninstall
/D="C:\Program Files\Hawkscope" - installation folder
----------------------------------------*/

; ----------------------------------------
; MODERN UI

!include MUI2.nsh

; ----------------------------------------
; GENERAL

Name "Hawkscope @REPL_VERSION@"
OutFile "dist\win\hawkscope-setup.exe"

;default installation folder
InstallDir "C:\Program Files\Hawkscope"
InstallDirRegKey HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\YaCy" "UninstallString"

;requested execution level on Vista
RequestExecutionLevel user

SetCompressor /SOLID LZMA
!insertmacro MUI_RESERVEFILE_LANGDLL ;loads faster

; ----------------------------------------
; JAVA VERSION

!define JRE_VERSION6 "1.6"
!define JRE_URL "http://javadl.sun.com/webapps/download/AutoDL?BundleId=26224" ;jre-6u11-windows-i586-p.exe

; ----------------------------------------
; GENERAL APPEARANCE

!define MUI_ICON "dist/etc/hawkscope.ico"
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\orange-uninstall.ico"

;!define MUI_WELCOMEFINISHPAGE_BITMAP ""

;!define MUI_HEADERIMAGE
;!define MUI_HEADERIMAGE_BITMAP ""
;!define MUI_HEADERIMAGE_BITMAP_NOSTRETCH

!define MUI_ABORTWARNING ;display warning before aborting installation

; ----------------------------------------
; INSTALLER PAGES

;!insertmacro MUI_PAGE_WELCOME

;!insertmacro MUI_PAGE_LICENSE license.txt

!define MUI_COMPONENTSPAGE_NODESC
!insertmacro MUI_PAGE_COMPONENTS
ComponentText "YaCy v@REPL_VERSION@ (Build @REPL_DATE@)"

!insertmacro MUI_PAGE_DIRECTORY

!insertmacro MUI_PAGE_INSTFILES

!insertmacro MUI_PAGE_FINISH

; ----------------------------------------
; UNINSTALLER PAGES

!insertmacro MUI_UNPAGE_CONFIRM

!insertmacro MUI_UNPAGE_INSTFILES

; ----------------------------------------
; LANGUAGES

!insertmacro MUI_LANGUAGE "English"

; ----------------------------------------
; INSTALLABLE MODULES

;InstType "Normal"

Section "Hawkscope"
    SectionIn 1 RO
    SetShellVarContext current
    RMDir /r "$SMPROGRAMS\Hawkscope" ;clear old shortcuts
    Delete "$QUICKLAUNCH\Hawkscope.lnk" ;old
    Delete "$SMSTARTUP\Hawkscope.lnk" ;old
    
    SetOutPath $INSTDIR
    ;set noindex attribute for windows indexing service
    Exec 'attrib +I "$INSTDIR"'
    Exec 'attrib +I "$INSTDIR\*" /S /D'
    
    File /r "dist/win/hawkscope.exe"

    WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\YaCy" "DisplayName" "Hawkscope"
    WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\YaCy" "UninstallString" '"$INSTDIR\uninstall.exe"'
    WriteUninstaller "uninstall.exe"
SectionEnd

Section "Java"
    SectionIn 1 RO
    SetShellVarContext current
    Call DetectJRE
SectionEnd

Section "Start Menu Group"
    SectionIn 1
    SetShellVarContext current
    CreateDirectory "$SMPROGRAMS\Hawkscope"
    CreateShortCut "$SMPROGRAMS\Hawkscope\Hawkscope.lnk" "$INSTDIR\hawkscope.exe" "" "$INSTDIR\hawkscope.ico" "" SW_SHOWMINIMIZED
    CreateShortCut "$SMPROGRAMS\YaCy\Uninstall.lnk" "$INSTDIR\Uninstall.exe"
SectionEnd

Section "YaCy in Startup"
    SetShellVarContext current
    CreateShortCut "$SMSTARTUP\Hawkscope.lnk" "$INSTDIR\hawkscope.exe" "" "$INSTDIR\hawkscope.ico" "" SW_SHOWMINIMIZED
SectionEnd

; ----------------------------------------
; UNINSTALLER

Section "Uninstall"
    SetShellVarContext current

    Delete "$INSTDIR\*.*"

    ;delete all
    RMDir /r "$INSTDIR"
    
    DeleteRegKey HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\YaCy"
    nouninstall:
SectionEnd

; ----------------------------------------
; FUNCTIONS

Function GetJRE
; based on http://nsis.sourceforge.net/Simple_Java_Runtime_Download_Script  
    userInfo::getAccountType
    Pop $0
        StrCmp $0 "Admin" +3
        MessageBox MB_ICONEXCLAMATION "You need Administrator privileges to install Java. \
        It will now be downloaded to the shared documents folder. \
        YaCy won't run without Java." /SD IDOK
    
    SetShellVarContext all
    StrCpy $2 "$DOCUMENTS\Java Runtime Environment (install for hawkscope).exe"
    SetShellVarContext current
    nsisdl::download /TIMEOUT=30000 ${JRE_URL} $2
    Pop $R0 ;Get the return value
        StrCmp $R0 "success" +3
        MessageBox MB_OK "Download failed: $R0" /SD IDOK
        Return
    StrCmp $0 "Admin" +3
        CreateShortCut "$DESKTOP\Install Java for Hawkscope.lnk" "$2"
        Return ; don't delete if not admin
    ExecWait "$2 /s"
    Delete $2
FunctionEnd

Function DetectJRE
    ReadRegStr $2 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" "CurrentVersion"
    StrCmp $2 ${JRE_VERSION6} doneDetectJRE
    Call GetJRE
    doneDetectJRE:
FunctionEnd