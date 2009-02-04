;$Id$
;(c) 2009 by Tomas Varaneckas

!include "MUI.nsh"

Name "Hawkscope"

outFile "dist\output\hawkscope-${VERSION}-installer.exe"

installDir $PROGRAMFILES\Hawkscope\

BrandingText " "

!define MUI_ABORTWARNING
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_LICENSEPAGE_TEXT_TOP "Please read the Licence Agreement:"
!define MUI_LICENSEPAGE_TEXT_BOTTOM "For more information visit http://hawkscope.googlecode.com"
!define MUI_LICENSEPAGE_BUTTON "I agree"
!insertmacro MUI_PAGE_LICENSE "license.txt"
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
!insertmacro MUI_LANGUAGE "English"
 
section
    setOutPath $INSTDIR
    file dist\output\win\Hawkscope.exe
    file changelog.txt
    writeUninstaller "$INSTDIR\uninstall.exe"
    createShortCut "$SMPROGRAMS\Startup\Hawkscope.lnk" "$INSTDIR\Hawkscope.exe"
    createDirectory "$SMPROGRAMS\Hawkscope"
    createShortCut "$SMPROGRAMS\Hawkscope\Hawkscope.lnk" "$INSTDIR\Hawkscope.exe"
    createShortCut "$SMPROGRAMS\Hawkscope\Uninstall.lnk" "$INSTDIR\uninstall.exe"
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Hawkscope" \
                 "DisplayName" "Hawkscope - Access anything with single click!"
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Hawkscope" \
                 "UninstallString" "$INSTDIR\uninstall.exe"
sectionEnd
 
section "uninstall"
    delete "$INSTDIR\Hawkscope.exe"
    delete "$INSTDIR\changelog.txt"
    delete "$INSTDIR\uninstall.exe"
    RMDir /r "$INSTDIR"
    delete "$SMPROGRAMS\Startup\Hawkscope.lnk"
    delete "$SMPROGRAMS\Hawkscope\Hawkscope.lnk"
    delete "$SMPROGRAMS\Hawkscope\Uninstall.lnk"
    RMDir /r "$SMPROGRAMS\Hawkscope"
    DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Hawkscope"
sectionEnd