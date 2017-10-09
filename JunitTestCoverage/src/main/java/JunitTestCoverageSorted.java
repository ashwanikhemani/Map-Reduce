import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.*;

public class JunitTestCoverageSorted {

    public static class TestMapper extends Mapper<Object, Text, Text ,Text  > {
        private Text word = new Text();
        private Text one = new Text();
        static int line_coverage;


        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            InputSplit currentSplit = context.getInputSplit();
            Path filePath = ((FileSplit) currentSplit).getPath();
            String filePathString = filePath.toString();
            String jname = null;
            String cname;
            int index;
            // find the test name
            index = filePathString.indexOf("Test");
            jname = filePathString.substring(index);
            int idx = jname.indexOf("/");
            String line_number = null;
            jname = jname.substring(0, idx);
            cname = filePath.getName().toString();
            index = cname.indexOf(".");
            cname = cname.substring(0, index);
            // find the line coverage from csv report

            if (filePath.getName().toString().contains("csv")) {
                String line = value.toString();
                if(line.contains(cname)) {
                    String[] elements = line.split(",");
                    line_coverage = Integer.parseInt(elements[8]);
                }
            }
            // find the line numbers covered from html report
            else {
                String line = value.toString();
                String id = null;
                String text = "id";
                int start = 0, end;
                if (line.startsWith("<span")) {
                    if (line.contains("class=\"fc\"")) {
                        start = line.indexOf(text);
                        id = line.substring(start + 5);
                        end = id.indexOf("\"");
                        line_number = id.substring(0, end);

                        line_number = line_number + "_" + cname;
                        word.set(line_number);

                    }
                }
            }
            //append line_coverage in value along with junit name
            one.set(jname+ "_" + line_coverage);
            context.write(word, one);
        }
    }


    public static class TestReducer extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values,
                           Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            String t1 = null;
            HashMap<String, Integer> test = new LinkedHashMap<String, Integer>();
            HashMap<String, Integer> testMap = new LinkedHashMap<String, Integer>();
            // sort the junit names by line coverage
            for (Text val : values) {
                String tmp = val.toString();
                String[] elements = tmp.split("_");
                int lc = Integer.parseInt(elements[1]);
                test.put(elements[0], lc);
                testMap=TestReducer.sortByValues(test);
            }
            for (String val : testMap.keySet()) {
                val = val + ",";
                sb.append(val);
            }
            context.write(key, new Text(sb.toString()));


        }

        private static HashMap sortByValues(HashMap map) {
            List list = new LinkedList(map.entrySet());
            // Defined Custom Comparator here
            Collections.sort(list, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((Comparable) ((Map.Entry) (o1)).getValue())
                            .compareTo(((Map.Entry) (o2)).getValue());
                }
            });

            //  copy the sorted list in HashMap
            // using LinkedHashMap to preserve the insertion order
            HashMap sortedHashMap = new LinkedHashMap();
            for (Iterator it = list.iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                sortedHashMap.put(entry.getKey(), entry.getValue());
            }
            return sortedHashMap;
        }
    }

    public static void main(String args[]) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "junit mapping ");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(TestMapper.class);
        job.setReducerClass(TestReducer.class);
        FileInputFormat.setInputDirRecursive(job, true);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setJarByClass(HDFSTest.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
