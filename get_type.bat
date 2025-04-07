(
echo.		
echo ====================================================================================================== 		
hostname														
date /t
echo ====================================================================================================== 	
getmac /v /fo list	
wmic computersystem get Manufacturer, Model, Name, Domain, UserName, TotalPhysicalMemory, SystemType /format:list
wmic bios get SerialNumber
wmic MEMORYCHIP get BankLabel, Capacity, DeviceLocator, MemoryType, TypeDetail, Speed /format:list
wmic os get Caption, Version, OSArchitecture /format:list
wmic cpu get caption, deviceid, name
wmic cpu get numberofcores, maxclockspeed, status
wmic logicaldisk get size,freespace,caption
wmic diskdrive get caption, mediatype
) >> raccoltaDati.txt