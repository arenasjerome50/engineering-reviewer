package com.philcst.www.engineeringreviewer.data;


import com.philcst.www.engineeringreviewer.R;

/**
 * A container class for static, hardcoded dataset of topics with corresponding images and descriptions
 * Soon This class will be remove and save the data into a database or some sort of Json asset
 */
public class TopicData {

    public static int[] images = {
            R.drawable.ic_atom,
            R.drawable.ic_axis
    };

    public static String[] titles = {
            "General Applied Science",
            "Engineering Mathematics"
    };

    public static String[] desc = {
            "Description No. 1",
            "Description No. 2"
    };

    public static String[][] subTopics = {
            {"abc", "def"},
            {   // 4 topics
                    "Systems of Numbers & Conversion",
                    "Fundamentals in Algebra",
                    "Quadratic Equation, Binomial Theorem & Logarithms",
                    "Age, Work, Mixture, Digit, Motion Problems"
            }};

    public static int[][] subTopicImages = {
            {R.drawable.ic_molecule, R.drawable.ic_molecule},
            {   // 4 topics
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral,
                    R.drawable.ic_integral
            }
    };

    public static String[][] subTopicdesc = {
            {
                    "Description No. 1",
                    "Description No. 2"
            },
            {
                    "Description No. 1",
                    "Description No. 2",
                    "Description No. 3",
                    "Description No. 4"
            }
    };

    public static String[][] subTopicContents = {
            {
                    "none.pdf",
                    "none.pdf"
            },
            {
                    "system_of_numbers_and_conversion.pdf",
                    "fundamentals_in_algebra.pdf",
                    "quadratic_equation_binomial_logarithm.pdf",
                    "age_work_mixture_digit_motion_problems.pdf"
            }
    };
}
