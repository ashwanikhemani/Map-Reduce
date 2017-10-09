import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;


public class JunitTestCoverage {
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
            index = filePathString.indexOf("Test");
            jname = filePathString.substring(index);
            int idx = jname.indexOf("/");
            String line_number = null;
            jname = jname.substring(0, idx);
            cname = filePath.getName().toString();
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
                    index = cname.indexOf(".");
                    cname = cname.substring(0, index);
                    line_number = line_number + "_" + cname;
                    word.set(line_number);
                    one.set(jname);
                    context.write(word, one);
                }
            }
        }
    }


    public static class TestReducer extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values,
                           Context context) throws IOException ,InterruptedException{
            StringBuilder sb = new StringBuilder();
            String t1=null;
            for (Text val : values) {
                t1=val.toString();
                t1=t1+",";
                sb.append(t1);
            }
            context.write(key, new Text(sb.toString()));
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
