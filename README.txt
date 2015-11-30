Procedure for getting Maven up and running (in Eclipse):
	1) Pull from repository
	2) Check for m2e plugin (Help -> Installation Details -> Plug-Ins, search "maven")
	3) Check for updates (Help -> Check for Updates)
	4) Set project to use JDK version 1.8 (Window -> Preferences -> Installed JREs)
	5) Right click on project and select Run As -> Maven Build. Set the goal to "compile" (no quotes)
	6) If no errors, then click RunModels -> Run As -> Java Application
	7) All should be well!
	

Using the Cluster/Datum/ClusterSet objects:
	1) Inputs need to be cast as Datums. A Datum first and foremost stores an input vector (minus the
	class variable, since this is clustering) as an ArrayList of doubles. Later, the assignToCluster()
	method can be used by a clustering algorithm to assign the Datum to a cluster. This is a bidirectional 
	association, as the Cluster object also stores its list of associated Datums.
	2) Each clustering algorithm should contain a run() method which takes in an ArrayList of Datums 
	and outputs an ArrayList of Clusters. 
	3) A Cluster object contains:
		a) centroids: an ArrayList of doubles, primarily used for methods that focus on representing the
		cluster as its centroid. This isn't applicable for all methods (e.g. competitive learning), so
		in that case I just used the array of weights as the centroid, since it's not actually used
		in cohesion/separation calculations anyway. 
		b) pts: an ArrayList of all the Datums assigned to this cluster by whatever method generated it.
		c) index: the index of this Cluster, i.e. its position in the ArrayList of Clusters returned
		by whatever method generated it
	4) Our equivalent of the RunModels class should feed the results of each algorithm's run() method 
	into a separate ClusterSet, which represents a full clustering of the data. This is useful for
	comparing the algorithms, since the ClusterSet object can calculate cohesion and separation for
	the results.
	
	