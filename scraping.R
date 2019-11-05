setwd("/Users/zacharyjohnson/Downloads/School/SportsAnalytics/Data")
winData <- read.csv(file="kickoffWon.csv", header=TRUE, sep=",", stringsAsFactors = FALSE)
winKickoffs <- winData[(winData$Time == "15:00"), ]

lossData <- read.csv(file="kickoffLost.csv", header=TRUE, sep=",", stringsAsFactors = FALSE)
lossKickoffs <- lossData[(lossData$Time == "15:00"), ]