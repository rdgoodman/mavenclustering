package clustering;

import java.util.ArrayList;
import java.util.Random;
/*
 * Performs unsupervised learning via kMeans clustering. Creates a variable amount of 
 * kMeans clusters, and these are used to split the data into clusters corresponding 
 * to the input data. The number of kMeans clusters is a tunable parameter. 
 */

import clustering.Distance;

public class KMeans {
	//variable to store the distance between clusters
    private Distance d = new Distance();
    //variable to store the maximum distance between clusters
    public double maxD;

    public KMeans() {

    }

    //creates the number of clusters (is a tunable parameter, resulting in the approximation
    //of ANY function given a sufficient number of clusters
    public ArrayList<ArrayList> createClusters(int k, ArrayList<ArrayList> in, int inDim) {
        //System.out.println(k + " : clusters");
        Random rand = new Random();
        //int indim = in.get(0).size(); //figure out dimensionality of input
        ArrayList<ArrayList> clusters = new ArrayList(); //initialize cluster array
        //clusters created randomly, refined through time
        for (int i = 0; i < k; i++) {//Generate k examples of dimensionality same as input
            clusters.add(new ArrayList());
            for (int j = 0; j < inDim; j++) {
                clusters.get(i).add(rand.nextInt(10) - 5);
            }
        }
        //System.out.println("begin clusters: " + clusters);
        //System.out.println("Clusters created: " + clusters);
        clusters = trainClusters(in, clusters, 5, inDim);
        //System.out.println("end clusters: " + clusters);
        return clusters;
    }

    //trains the clusters, which provides input to hidden layer
    public ArrayList<ArrayList> trainClusters(ArrayList<ArrayList> in, ArrayList<ArrayList> clusters, int iter, int dim) {
        //System.out.println(" in : " + in);
        //System.out.println(clusters);
        //System.out.println("data point set size " + in.size());
        int insize = in.size();
        //System.out.println("train clusters");
        for (int i = 0; i < insize; i++) {
            in.get(i).add(0.0);
        }
        //calculates distance for each datapoint in set, for each of the clusters
        for (int i = 0; i < iter; i++) {//number of iterations
            for (int j = 0; j < in.size(); j++) {//each datapoint

                double curD = 11;
                int smallestCluster = 0;
                for (int c = 0; c < clusters.size(); c++) {
                    //System.out.println("iteration: "+ i + ", datapoint: " + j + ", cluster: " + c);

                    double tempD = d.calculateDistance(in.get(j), clusters.get(c), dim);//find closest cluster
                    //System.out.println(" i: " + i + ", j: " + j + ", c: " + c+", tempD: " + tempD + ", curD: " + curD + ", input: " + in.get(j));
                    if (tempD < curD) {
                        smallestCluster = c;
                        curD = tempD;
                        //System.out.println(curD + " and " + smallestCluster);
                    }
                }
                in.get(j).set(dim, (double) smallestCluster);
                //System.out.println("smallest Cluster : " + in.get(j).get(dim));
                //System.out.println("cluster: " + clusters.get((int)in.get(j).get(dim)));
            }
            for (int c = 0; c < clusters.size(); c++) {//for each cluster
                //System.out.println(c);
                //double avg = 0;
                for (int l = 0; l < dim; l++) {//each dimension
                    //System.out.println("c: " + c + ", l: " + l);
                    double dimAvg = 0;
                    int count = 0;
                    for (int j = 0; j < insize; j++) {//each input
                        //ystem.out.println(in.get(j).get(dim));
                        double c3 = in.get(j).get(dim);
                        //System.out.println("c3: " + c3);
                        int c2 = (int) (c3);
                        //System.out.println("c2: " + c2);
                        if (c2 == c) {
                            int cast = (int) in.get(j).get(l);
                            //System.out.println("blergh: " + blergh);
                            dimAvg += (double) cast;
                            //System.out.println("Dim Avg: " + dimAvg);
                            count++;
                            //ystem.out.println(count);
                            double.class
                        }
                    }
                    if (count != 0) {
                        //System.out.println("DimAvg: " + dimAvg + ", count: " + count + ", result: " + dimAvg / count);
                        dimAvg = dimAvg / count;
                    } else {
                        dimAvg = 0;
                    }
                    clusters.get(c).set(l, dimAvg);
                }
            }
            //System.out.println(i + " : " + clusters);
        }
        maxD = 0;
        double curD = 0;
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = 0; j < clusters.size(); j++) {
                if (i != j) {
                    curD = d.calculateDistance(clusters.get(i), clusters.get(j), dim);
                    //System.out.println(curD);
                    if (curD > maxD) {
                        maxD = curD;
                    }
                }
            }
        }
        System.out.println("maxD: " + maxD);
        return clusters;
    }
}


