;$Id$
;(c) 2009 by Tomas Varaneckas

# define name of installer
outFile "dist\output\hawkscope-installer.exe"

 
# define installation directory
installDir $PROGRAMFILES\Hawkscope\
 
# start default section
section
 
    
    # set the installation directory as the destination for the following actions
    setOutPath $INSTDIR
 
    file dist\output\win\Hawkscope.exe

    # create the uninstaller
    writeUninstaller "$INSTDIR\uninstall.exe"
 
    # create a shortcut named "new shortcut" in the start menu programs directory
    # point the new shortcut at the program uninstaller
    
    createShortCut "$SMPROGRAMS\Startup\Hawkscope.lnk" "$INSTDIR\Hawkscope.exe"
    createDirectory "$SMPROGRAMS\Hawkscope"
    createShortCut "$SMPROGRAMS\Hawkscope\Hawkscope.lnk" "$INSTDIR\Hawkscope.exe"
    createShortCut "$SMPROGRAMS\Hawkscope\Uninstall.lnk" "$INSTDIR\uninstall.exe"
    
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Hawkscope" \
                 "DisplayName" "Hawkscope - Access anything with single click!"
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Hawkscope" \
                 "UninstallString" "$INSTDIR\uninstall.exe"
sectionEnd
 
# uninstaller section start
section "uninstall"
 
    # first, delete the uninstaller
    delete "$INSTDIR\Hawkscope.exe"
    delete "$INSTDIR\uninstall.exe"
    RMDir /r "$INSTDIR"
 
    # second, remove the link from the start menu
    delete "$SMPROGRAMS\Startup\Hawkscope.lnk"
    delete "$SMPROGRAMS\Hawkscope\Hawkscope.lnk"
    delete "$SMPROGRAMS\Hawkscope\Uninstall.lnk"
    RMDir /r "$SMPROGRAMS\Hawkscope"
    
    DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Hawkscope"
 
# uninstaller section end
sectionEnd