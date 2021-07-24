#!/bin/bash
cd ../../locserver/ || exit
java -Xmx2048M -Xms2048M -DIReallyKnowWhatIAmDoingISwear=true -jar latest.jar

