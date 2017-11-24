package com.gary.spiders.util;

import java.util.List;

public class WordSearch
{
    public static Grid generateWordSearch(String[] args, List<String> words)
    {
        WordSearchConfig wordSearchConfig = WordSearchConfig.buildWordSearchConfigFromArgs(args, words);
        WordSearchGenerator wordSearchGenerator = new WordSearchGenerator(wordSearchConfig);
        Grid grid = wordSearchGenerator.generate();
        return grid;
    }
}