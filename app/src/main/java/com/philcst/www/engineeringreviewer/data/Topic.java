package com.philcst.www.engineeringreviewer.data;


import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.philcst.www.engineeringreviewer.R;
import com.philcst.www.engineeringreviewer.interfaces.ListItemShowable;

import java.util.ArrayList;

public class Topic implements Parcelable, ListItemShowable{
    private int icon;
    private String title;
    private String desc;
    private ArrayList<Topic> subTopics;
    private String content;

    public Topic(int icon, String title, String desc, ArrayList<Topic> subTopics){
        this.icon = icon;
        this.title = title;
        this.desc = desc;
        this.subTopics = subTopics;
        this.content = null;
    }

    public Topic(int icon, String title, String desc, String content){
        this.icon = icon;
        this.title = title;
        this.desc = desc;
        this.subTopics = null;
        this.content = content;
    }


    public Topic(Parcel in) {
        this.icon = in.readInt();
        this.title = in.readString();
        this.desc = in.readString();
        // instantiate an ArrayList first
        this.subTopics = new ArrayList<>();
        // and read from parcel
        in.readTypedList(subTopics, Topic.CREATOR);
        this.content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(icon);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeTypedList(subTopics);
        dest.writeString(content);
    }

    public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //public int getImageId() {
    //    return imageId;
    //}

    //public String getTitle() {
    //    return title;
    //}

    //public String getDesc() { return desc; }


    @Override
    public int getIcon() {
        return icon;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getDescription() {
        return desc;
    }

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
            R.drawable.ic_geas,
            R.drawable.ic_engg_math
    };

    private static int[][] subTopicImages = {
            {   // Geas
                    R.drawable.ic_molecule,
                    R.drawable.ic_statics,
                    R.drawable.ic_dynamics,
                    R.drawable.ic_engg_economy_one,
                    R.drawable.ic_engg_economy_two
            },
            {   // Engineering Math
                    R.drawable.ic_systems_of_numbers_and_conversion,
                    R.drawable.ic_algebra,
                    R.drawable.ic_binomial,
                    R.drawable.ic_age_work,
                    R.drawable.ic_clock_variation,
                    R.drawable.ic_venn_diagram,
                    R.drawable.ic_plane_geometry,
                    R.drawable.ic_solid_geometry,
                    R.drawable.ic_plane_trigonometry,
                    R.drawable.ic_spherical_trigonometry,
                    R.drawable.ic_analytic_one,
                    R.drawable.ic_analytic_two,
                    R.drawable.ic_differential,
                    R.drawable.ic_differential2,
                    R.drawable.ic_integral2,
                    R.drawable.ic_differential_equations,
                    R.drawable.ic_adv_math
            }
    };

    /*
    Hard coded content file names
     */
    private static String[][] subTopicContents = {
            {   // Geas
                    "physics.html",
                    "statics.html",
                    "dynamics.html",
                    "engg_economy_one.html",
                    "engg_economy_two.html"
            },
            {   // Engineering math
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
                    "differential_calculus_maxima_minima_time_rates.html",
                    "integral_calculus.html",
                    "differential_equations.html",
                    "advanced_engg_math.html"
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

    public static ArrayList<Topic> getMainMenu(Resources res) {
        ArrayList<Topic> mainMenuItems = new ArrayList<>();

        int[] icons = {
                R.drawable.ic_review,
                R.drawable.ic_start_quiz,
                R.drawable.ic_settings,
                R.drawable.ic_about
        };

        String[] mainMenuTitles = res.getStringArray(R.array.main_menu_titles);
        String [] mainMenuDescription = res.getStringArray(R.array.main_menu_description);

        for (int x = 0; x < icons.length; x++) {
            mainMenuItems.add(new Topic(icons[x], mainMenuTitles[x], mainMenuDescription[x], "menu"));
        }

        return mainMenuItems;
    }
}
