package com.philcst.www.engineeringreviewer.data;


import android.content.res.Resources;

import com.philcst.www.engineeringreviewer.R;

import java.util.ArrayList;

public class Topic {
    private int imageId;
    private String title;
    private String desc;
    private ArrayList<Topic> subTopics;
    private String content;

    public Topic(int imageId, String title, String desc, ArrayList<Topic> subTopics){
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
        this.subTopics = subTopics;
        this.content = null;
    }

    public Topic(int imageId, String title, String desc, String content){
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
        this.subTopics = null;
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() { return desc; }

    public boolean hasSubTopics() {
        return subTopics != null;
    }

    public Topic getSubTopic(int position) {
        return subTopics.get(position);
    }

    public ArrayList<Topic> getSubTopicList() {
        return subTopics;
    }

    public String getContent() {
        return content;
    }

    /*
    Hard Coded Icon reference
     */
    private static int[] images = {
            R.drawable.ic_atom,
            R.drawable.ic_axis
    };

    private static int[][] subTopicImages = {
            {R.drawable.ic_molecule, R.drawable.ic_molecule},
            {   // 4 topics
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral
            }
    };

    /*
    Hard coded content file names
     */
    private static String[][] subTopicContents = {
            {
                    "age_work_mixture_digit_motion_problems.html",
                    "analytic_geometry_one.html"
            },
            {
                    "systems_of_numbers_and_conversion.html",
                    "fundamentals_in_algebra.html",
                    "quadratic_equation_binomial_theorem_logarithm.html",
                    "age_work_mixture_digit_motion_problems.html",
                    "clock_variation_miscellaneous_problems_progression.html",
                    "venn_diagram_permutation_combination_probability.html",
                    "plane_geometry.html",
                    "solid_geometry.html",
                    "plane_trigonometry.html",
                    "spherical_trigonometry.html",
                    "analytic_geometry_one.html",
                    "analytic_geometry_two.html",
                    "differential_calculus_limits_derivatives.html",
                    "differential_calculus_maxima_minima_time_rates,html",
                    "integral_calculus.html",
                    "differential_equations.html"
            }
    };

    // function for assigning the data to these containers members, mTopicItems, mGeasSubTopicItems
    // and mEnggMathSubTopicItems
    // TODO: we need to rethink how the data is access that is not hard coded to a class instead you
    // can query data to the database.
    public static ArrayList<Topic> loadTopicData(Resources res) {
        ArrayList<Topic> topicItems = new ArrayList<>();

        String[] titles = res.getStringArray(R.array.main_topic_names);
        String[] desc = res.getStringArray(R.array.main_topic_desc);

        for (int x = 0; x < titles.length; x++) {

            ArrayList<Topic> subTopics = new ArrayList<>();

            String[][] subTopicTitles = {
                    res.getStringArray(R.array.geas_topic_names),
                    res.getStringArray(R.array.engg_math_topic_names)
            };

            String[][] subTopicDesc = {
                    res.getStringArray(R.array.geas_topic_desc),
                    res.getStringArray(R.array.engg_math_topic_desc)
            };

            for (int y = 0; y < subTopicTitles[x].length; y++) {
                subTopics.add(y, new Topic(subTopicImages[x][y],
                        subTopicTitles[x][y],
                        subTopicDesc[x][y],
                        subTopicContents[x][y]
                ));
            }


            topicItems.add(x, new Topic(images[x], titles[x], desc[x], subTopics));
        }

        return topicItems;
    }
}
