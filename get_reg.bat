(
echo run-as net.egordmitriev.cheatsheets
echo cp ./databases/cs_registry.db /sdcard/
echo exit
echo exit
) | adb shell
adb pull /sdcard/cs_registry.db ./reg.db