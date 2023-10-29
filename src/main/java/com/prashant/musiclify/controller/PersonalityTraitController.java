package com.prashant.musiclify.controller;

import com.prashant.musiclify.constant.ApiPath;
import com.prashant.musiclify.constant.Template;
import com.prashant.musiclify.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
@AllArgsConstructor
public class PersonalityTraitController {

    private final SavedTrackService savedTrackService;
    private final SavedAlbumService savedAlbumService;
    private final RecentPlayesTrackService recentPlayedTrackService;
    private final GetArtistService getArtistService;
    private final TopArtistService topArtistService;
    private final TopTrackService topTrackService;

    @GetMapping(value = ApiPath.PERSONALITY_TRAIT, produces = MediaType.TEXT_HTML_VALUE)
    public String getPersonalityTrait(final HttpSession session, final Model model) {
        String accessToken = session.getAttribute("accessToken").toString();
//        Object albums = savedAlbumService.getAlbums(accessToken);
//        Object tracks = savedTrackService.getTracks(accessToken);
//        Object history = recentPlayedTrackService.getHistory(accessToken);
        Object topArtists = topArtistService.getTopArtists(accessToken, 2);
        Map<String, Integer> genreCounts = new HashMap<>();
        String dominantGenre;

        Map<String, String> genrePersonalityMap = new HashMap<String, String>();
        genrePersonalityMap.put("pop", "Extroverted, honest, and conventional");
        genrePersonalityMap.put("rap", "High self-esteem and more outgoing");
        genrePersonalityMap.put("hip hop", "High self-esteem and more outgoing");
        genrePersonalityMap.put("country", "Hardworking, conventional, outgoing, and conservative");
        genrePersonalityMap.put("rock", "Gentle, creative, and introverted");
        genrePersonalityMap.put("heavy metal", "Gentle, creative, and introverted");
        genrePersonalityMap.put("indie", "Introverted, intellectual, and creative");
        genrePersonalityMap.put("dance", "Outgoing, assertive, and open to experience");
        genrePersonalityMap.put("classical", "Creativity and healthy self-esteem");
        genrePersonalityMap.put("jazz", "Extroverted with high self-esteem");
        genrePersonalityMap.put("blues", "Tend to be very creative, intelligent, and at ease");
        genrePersonalityMap.put("soul", "Extroverted with high self-esteem");

//        Pop. Extroverted, honest, and conventional. Although pop music lovers were hardworking and had high self-esteem, researchers suggest that they are less creative and more uneasy than those enamored by other musical styles.
//        Rap/hip hop. Despite the stereotype that rap lovers are aggressive or violent, the researchers found no such link. However, the rap fans tended to have high self-esteem and were generally more outgoing than fans of other styles.
//        Country. These fans typically identified as hardworking, conventional, outgoing, and conservative. Although country music frequently centers on heartbreak, people who prefer it tended to be emotionally stable. They also ranked lower than others in openness to experience.
//        Rock/heavy metal. Rock and heavy metal often project images of anger, bravado, and aggression. However, this study found such fans to be gentle, creative, and introverted. They also tended to have low self-esteem.
//        Indie. Fans of the indie genre registered as introverted, intellectual, and creative, but less hardworking and gentle than fans of other styles. Passivity, anxiousness, and low self-esteem were other notable personality characteristics.
//        Dance. Those who preferred dance music were typically outgoing, assertive, and open to experience but ranked lower than others in gentleness.
//        Classical. The study's classical music lovers were generally somewhat introverted but at ease with themselves. Creativity and healthy self-esteem were common among them.
//        Jazz, blues, and soul. Extroverted with high self-esteem. They also tend to be very creative, intelligent, and at ease.
//
//        if (history != null) {
//            LinkedHashMap<String, Object> historyMap = (LinkedHashMap<String, Object>) history;
//            LinkedHashMap<String, Object> trackMap = (LinkedHashMap<String, Object>) tracks;
//
//            // Access the "items" array
//            List<LinkedHashMap<String, Object>> items1 = (List<LinkedHashMap<String, Object>>) historyMap.get("items");
//            List<LinkedHashMap<String, Object>> items2 = (List<LinkedHashMap<String, Object>>) trackMap.get("items");
//
//            Set<LinkedHashMap<String, Object>> items = new HashSet<>();
//
//            // Add all items from the first list
//            items.addAll(items1);
//            // Add all items from the second list
//            items.addAll(items2);
//
//            for (LinkedHashMap<String, Object> item : items) {
//                LinkedHashMap<String, Object> track = (LinkedHashMap<String, Object>) item.get("track");
//                List<LinkedHashMap<String, Object>> artists = (List<LinkedHashMap<String, Object>>) track.get("artists");
//
//                List<String> genres = (List<String>) item.get("genres");
//                for (LinkedHashMap<String, Object> artist : artists) {
//                    String artistId = (String) artist.get("id");
//                    LinkedHashMap<String, Object> art = (LinkedHashMap<String, Object>) getArtistService.getArtist(accessToken, artistId);
//
////                    String name = (String) artist.get("name");
////                    System.out.println("Artist ID: " + artistId + " Artist name:" + art.get("name") + " Genre:" + art.get("genres"));
//                    List<String> genres = (List<String>) art.get("genres");

        if (topArtists != null) {
            LinkedHashMap<String, Object> topArtistsMap = (LinkedHashMap<String, Object>) topArtists;
            List<LinkedHashMap<String, Object>> items = (List<LinkedHashMap<String, Object>>) topArtistsMap.get("items");
            for (LinkedHashMap<String, Object> item : items) {
                List<String> genres = (List<String>) item.get("genres");
                for (String genre : genres) {
                    genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
                }
            }
//            for (Map.Entry<String, Integer> entry : genreCounts.entrySet()) {
//                System.out.println(entry.getKey() + ": " + entry.getValue());
//            }

            // Determine Personality Traits
            dominantGenre = Collections.max(genreCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
            String personalityTraits = genrePersonalityMap.getOrDefault(dominantGenre, "Personality traits not available");

            //Display Results
//            System.out.println("Based on your music preferences, you are characterized as:");
//            System.out.println(personalityTraits);
            model.addAttribute("personalityTrait", personalityTraits);
        }
        return Template.PERSONALITY_TRAIT;
    }
}
