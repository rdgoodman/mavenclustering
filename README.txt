Procedure for getting Maven up and running (in Eclipse):
	1) Pull from repository
	2) Check for m2e plugin (Help -> Installation Details -> Plug-Ins, search "maven")
	3) Check for updates (Help -> Check for Updates)
	4) Set project to use JDK version 1.8 (Window -> Preferences -> Installed JREs)
	5) Right click on project and select Run As -> Maven Build. Set the goal to "compile" (no quotes)
	6) If no errors, then click RunModels -> Run As -> Java Application
	7) All should be well!