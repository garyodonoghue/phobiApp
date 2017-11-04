package com.gary.spiders.util;

public class WordSearch
{
    public static void generateWordSearch(String[] args)
    {
        WordSearchConfig wordSearchConfig = WordSearchConfig.buildWordSearchConfigFromArgs(args);
        WordSearchGenerator wordSearchGenerator = new WordSearchGenerator(wordSearchConfig);
        Grid grid = wordSearchGenerator.generate();
    }
}