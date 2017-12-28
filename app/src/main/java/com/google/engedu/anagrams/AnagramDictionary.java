/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private static int wordLength = DEFAULT_WORD_LENGTH;
    // Random Class to generate Random Numbers
    private Random random = new Random();
    //datastructures to hold information
    ArrayList<String> WordList = new ArrayList<>();
    HashSet<String> WordSet = new HashSet<>();
    HashMap<String, ArrayList<String>> LettersToWord = new HashMap<>();
    HashMap<Integer, ArrayList<String>> SizeToWords = new HashMap<>();


    public String sortLetters(String word){
        char[] characters = word.toCharArray();
        Arrays.sort(characters);
        String SortedWord = new String(characters);
        return SortedWord;
    }

    public AnagramDictionary(InputStream wordliststream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordliststream));
        String line;
        ArrayList<String> WordMapList;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            WordSet.add(word);
            WordList.add(word);
            //populate the SizeToWords hashmap
            if (SizeToWords.containsKey(word.length())){
                WordMapList = SizeToWords.get(word.length());
                WordMapList.add(word);
                SizeToWords.put(word.length(),WordMapList);
            }
            else {
                ArrayList<String> NewWordList = new ArrayList<>();
                NewWordList.add(word);
                SizeToWords.put(word.length(),NewWordList);
            }
            ArrayList<String> sortedList = new ArrayList<>();
            String sortedWord = sortLetters(word);

            // Populate the lettersToWord HashMap
            if ( !(LettersToWord.containsKey(sortedWord)) ) {
                sortedList.add(word);
                LettersToWord.put(sortedWord, sortedList);
            } else {
                sortedList = LettersToWord.get(sortedWord);
                sortedList.add(word);
                LettersToWord.put(sortedWord, sortedList);
            }
        }
    }
    // This function checks if the word is valid.
    // the provided word is a valid dictionary word (i.e., in wordSet), and
    // the word does not contain the base word as a substring.
    public boolean isGoodWord(String word, String base) {
        if (WordSet.contains(word) && !(base.contains(word))) {
            return true;
        }
        else
        {return false;}
    }
    // Default Anagram Fetcher
    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<>();
        return result;
    }


    //anagram fetcher for two more extra letters
    public ArrayList<String> getAnagramsWithTwoMoreLetters(String word){
        ArrayList<String> TempList;
        ArrayList<String> ResulitList = new ArrayList<>();
        for(char Firstalphabet = 'a';Firstalphabet<='z';Firstalphabet++){
            for (char SecondAlphabet = 'a';SecondAlphabet<='z';SecondAlphabet++){
                String anagram = word + Firstalphabet + SecondAlphabet;
                String SortedAnagram = sortLetters(anagram);
                if (LettersToWord.containsKey(SortedAnagram)){
                    TempList = LettersToWord.get(SortedAnagram);
                    for (int i=0;i<TempList.size();i++){
                        if(!(TempList.get(i).contains(word))){
                            ResulitList.add(TempList.get(i));
                        }
                    }
                }
            }
        }
        return ResulitList;
    }
    //anagram fetcher for one more extra ketter
    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> TempList;
        for (char alphabet = 'a';alphabet <='z';alphabet++)
        {
            String anagram = word + alphabet;
            String SortedAnagram = sortLetters(anagram);
            if(LettersToWord.containsKey(SortedAnagram)){
                TempList = LettersToWord.get(SortedAnagram);
                for (int i=0; i<TempList.size();i++){
                    if (!(TempList.get(i).contains(word))){
                        result.add(TempList.get(i));
                    }
                }

            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int Randomnumber;
        String Starterword;
        do {
            Randomnumber = random.nextInt(SizeToWords.get(wordLength).size());
            Starterword = SizeToWords.get(wordLength).get(Randomnumber);
        }while (getAnagramsWithTwoMoreLetters(Starterword).size()<MIN_NUM_ANAGRAMS);
if (wordLength < MAX_WORD_LENGTH){
    wordLength++;
}
        return Starterword;
    }
}
