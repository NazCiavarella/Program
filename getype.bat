getmac /v /fo list >> raccoltaDati.txt

wmic computersystem get Manufacturer, Model, Name, Domain, UserName, TotalPhysicalMemory, SystemType /format:list>> raccoltaDati.txt


wmic bios get SerialNumber >> raccoltaDati.txt


wmic MEMORYCHIP get BankLabel, Capacity, DeviceLocator, MemoryType, TypeDetail, Speed /format:list>> raccoltaDati.txt


wmic os get Caption, Version, OSArchitecture /format:list >> raccoltaDati.txt


echo ------------------------------------------------------------------------------------------------- >> raccoltaDati.txt






