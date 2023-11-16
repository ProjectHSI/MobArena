# Generate ProtocolBuffers

# Static Configuration
$protobufSrcDirectory = "./src/main/resources/protobuf"
$protobufOutDirectory = "./src/main/generated/"
$protoc = "protoc"

# Code
$protoFiles = Get-ChildItem -Path $protobufSrcDirectory

#Get-ChildItem -Recurse -Include *.proto -Path $protobufSrcDirectory | ForEach-Object {
    #Write-Host $protoFiles
    #Write-Host $protoFile


    #Invoke-Expression $configCommandString
#};

foreach ($protoFile in $protoFiles)
{
    $protoFileName = $protoFile.Name

    $configCommandString = "${protoc} -I ${protobufSrcDirectory} --java_out ${protobufOutDirectory} ${protobufSrcDirectory}/${protoFileName}"
    Write-Host $configCommandString
    Invoke-Expression $configCommandString
}