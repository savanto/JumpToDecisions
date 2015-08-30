package com.savanto.jumptodecisions;

import java.util.Random;


public class Decisions {
    private static final Random RAND = new Random();

    /* TODO -- this may not be the best data structure for storing the words. It depends on what
     * kind of operations we want to support. For randomly choosing indices, it's great; but what
     * about making sure that the same words don't get chosen more than once? Or if we make a more
     * complex hierarchy of types of words. Etc.
     */
    private final String[] words;


    /**
     * Create a new Decisions object. Load decision words from resources file and prepare pool of
     * words to be used for decision making.
     * @param words String[] of words used for making decisions.
     */
    public Decisions(String[] words) {
        this.words = words;
    }

    /**
     * Get a random word from the pool of decision words. See note above. This method naively
     * selects words at random, but they may be repeated, or it might compare nouns with verbs, etc.
     * @return A String with a random word from the pool.
     */
    public String getRandomWord() {
        return this.words[RAND.nextInt(this.words.length)];
    }
}
