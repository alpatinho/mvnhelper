' Shell-Exec.vbs
WScript.Echo WinRun( "cmd /c ipconfig /all")

Function WinRun( cmd )
    Dim shell, sResult
    Set shell = CreateObject("WScript.Shell")
    Set proc = shell.Exec( cmd )
    While proc.Status = 0
        WScript.Echo "sleeping..."
        WScript.Sleep 100
    Wend
    MSgbox "complete - hit ok to display results"
    WinRun = proc.StdOut.ReadAll()
End Function
