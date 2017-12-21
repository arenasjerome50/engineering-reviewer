package com.philcst.www.engineeringreviewer.data;


import com.philcst.www.engineeringreviewer.R;

import java.util.ArrayList;

/**
 * A container class for static, hardcoded dataset of topics with corresponding images and descriptions
 * Soon This class will be remove and save the data into a database or some sort of Json asset
 */
public class TopicData {

    public static ArrayList<Topic> topicItems = new ArrayList<>();


    public static int[] images = {
            R.drawable.ic_atom,
            R.drawable.ic_axis
    };

    //public static String[] titles = {
    //        "General Applied Science",
    //        "Engineering Mathematics"
    //};

    /*public static String[] desc = {
            "A discipline of science that applies existing scientific knowledge " +
                    "to develop more practical applications, like technology or " +
                    "inventions.",
            "The art of applying mathematics to complex real-world problems." +
                    " It combines mathematical theory, practical engineering " +
                    "and scientific computing to address today’s technological " +
                    "challenges."
    };*/

    /*public static String[][] subTopicTitles = {
            {"abc", "def"},
            {   // 2 topics
                    "Systems of Numbers & Conversion",
                    "Fundamentals in Algebra",
                    "Quadratic Equation, Binomial Theorem & Logarithms",
                    "Work, Mixture, Digit, Motion Problems",
                    "Clock, Variation, Misc. Problems & Progression",
                    "Venn Diagram, Permutation, Combination & Probability",
                    "Plane Geometry",
                    "Solid Geometry",
                    "Plane Trigonometry",
                    "Spherical Trigonometry",
                    "Analytic Geometry I",
                    "Analytic Geometry II"
            }};*/

    public static int[][] subTopicImages = {
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

    /*public static String[][] subTopicDesc = {
            {
                    "Description No. 1",
                    "Description No. 2"
            },
            {
                    "System of Numbers and how to convert unit of measurement.",
                    "Basic rules of Algebra, Properties of Equality, Zero, Exponents, Radicals, and Proportion, Special Products, and the Remainder & Factor Theorem.",
                    "Quadratic Equation, Binomial Theorem, Logarithms and It's properties.",
                    "Solve problems about age, work, mixture, digit and motion.",
                    "Solve about clock, variation, and other problems. learn about progressions",
                    "Learn how to use venn diagram, Counting possible outcomes using permutation, combination, and learn what is probability theory.",
                    "What is geometry, angles, circles, polygons, triangles, quadrilaterals, parallelograms and how to solve them.",
                    "Learn about Polyhedrons, its properties, and how to get its surface area and volume for every kind of it.",
                    "Study about triangles and it's properties.",
                    "Solving triangular surfaces in a sphere and its applications.",
                    "What is rectangular coordinates system, formulas for points, lines, and circle.",
                    "Parabola, Ellipse, Hyperbola, and its properties."
            }
    };*/

    public static String[][] subTopicContents = {
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
    /*public static void loadData() {

        for (int x = 0; x < titles.length; x++) {

            ArrayList<Topic> subTopics = new ArrayList<>();

            for (int y = 0; y < subTopicTitles[x].length; y++) {
                subTopics.add(y, new Topic(subTopicImages[x][y],
                        subTopicTitles[x][y],
                        subTopicDesc[x][y],
                        subTopicContents[x][y]
                ));
            }
            topicItems.add(x, new Topic(images[x],
                    titles[x],
                    desc[x],
                    subTopics
            ));
        }
    }

    public static ArrayList<Topic> getTopicItems() {
        return topicItems;
    }
    */
}
