setwd("/Users/zacharyjohnson/Downloads/School/SportsAnalytics/project/NFL-Overtime-Changes/Data")

## Post Rule Change: 2012-2016 Seasons
postRuleWin <- read.csv(file="kickoffWon.csv", header=TRUE, sep=",", stringsAsFactors = FALSE)
postRuleWinKickoffs <- postRuleWin[(postRuleWin$Time == "15:00"), ]

postRuleLoss <- read.csv(file="kickoffLost.csv", header=TRUE, sep=",", stringsAsFactors = FALSE)
postRuleLossKickoffs <- postRuleLoss[(postRuleLoss$Time == "15:00"), ]

## Pre Rule Change: 2007-2011 Seasons

preRuleWin <- read.csv(file="07-11KickoffWon.csv", header=TRUE, sep=",", stringsAsFactors = FALSE)
preRuleLoss <- read.csv(file="07-11KickoffLost.csv", header=TRUE, sep=",", stringsAsFactors = FALSE)

