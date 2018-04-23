package Threads;

import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Mean {

    static double[] array;
    static BlockingQueue<Double> results = new ArrayBlockingQueue<Double>(100);


    static void initArray(int size){
        array = new double[size];
        for(int i=0;i<size;i++){
            array[i]= Math.random()*size/(i+1);
        }
    }

    public static void main(String[] args) {
        initArray(100000000);

        //new MeanCalc(0,100000000).run();
        MeanCalc.parallelMean(16);
    }

    static class MeanCalc extends Thread{
        private final int start;
        private final int end;
        double mean = 0;

        MeanCalc(int start, int end){
            this.start = start;
            this.end=end;
        }
        public void run(){
            for(int i=this.start; i<this.end; i++){
                this.mean+=array[i];
            }
            mean/=end-start;
            System.out.printf(Locale.US,"%d-%d mean=%f\n",start,end,mean);
            try {
                results.put(mean);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * Oblicza średnią wartości elementów tablicy array uruchamiając równolegle działające wątki.
         * Wypisuje czasy operacji
         * @param cnt - liczba wątków
         */
        static void parallelMean(int cnt){
            // utwórz tablicę wątków
            MeanCalc threads[]=new MeanCalc[cnt];
            // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
            int offset = array.length/cnt;
            for (int i=0; i<cnt; i++){
                threads[i] = new MeanCalc(i*offset,(i+1)*offset);
            }
            // załóż, że array.length dzieli się przez cnt)
            double t1 = System.nanoTime()/1e6;
            //uruchom wątki
            double mean = 0;

            for(MeanCalc thread : threads){
                thread.start();
                try {
                    mean += results.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            double t2 = System.nanoTime()/1e6;

            // oblicz średnią ze średnich
            /*
            for(MeanCalc thread : threads){
                mean+=thread.mean;
            }
            */
            mean/=cnt;

            double t3 = System.nanoTime()/1e6;
            System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                    array.length,
                    cnt,
                    t2-t1,
                    t3-t1,
                    mean);
        }
    }

}
