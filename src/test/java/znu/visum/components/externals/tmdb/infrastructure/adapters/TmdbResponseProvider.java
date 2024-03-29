package znu.visum.components.externals.tmdb.infrastructure.adapters;

import okhttp3.mockwebserver.MockResponse;
import org.springframework.http.MediaType;

public class TmdbResponseProvider {

  public static MockResponse configurationResponse() {
    return new MockResponse()
        .setResponseCode(200)
        .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
        .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
        .setBody(
            """
            {
              "images": {
                "base_url": "http://image.tmdb.org/t/p/",
                "secure_base_url": "https://image.tmdb.org/t/p/",
                "backdrop_sizes": [
                  "w300",
                  "w780",
                  "w1280",
                  "original"
                ],
                "logo_sizes": [
                  "w45",
                  "w92",
                  "w154",
                  "w185",
                  "w300",
                  "w500",
                  "original"
                ],
                "poster_sizes": [
                  "w92",
                  "w154",
                  "w185",
                  "w342",
                  "w500",
                  "w780",
                  "original"
                ],
                "profile_sizes": [
                  "w45",
                  "w185",
                  "h632",
                  "original"
                ],
                "still_sizes": [
                  "w92",
                  "w185",
                  "w300",
                  "original"
                ]
              },
              "change_keys": [
                "adult",
                "air_date",
                "also_known_as",
                "alternative_titles"]
            }
            """
        );
  }

  public static MockResponse upcomingMoviesResponse() {
    return new MockResponse()
        .setResponseCode(200)
        .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
        .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
        .setBody(
                """
                {
                 "dates": {
                  "maximum": "2022-05-02",
                  "minimum": "2022-04-15"
                 },
                 "page": 1,
                 "results": [
                  {
                   "adult": false,
                   "backdrop_path": "/egoyMDLqCxzjnSrWOz50uLlJWmD.jpg",
                   "genre_ids": [
                    28,
                    878,
                    35,
                    10751
                   ],
                   "id": 675353,
                   "original_language": "en",
                   "original_title": "Sonic the Hedgehog 2",
                   "overview": "After settling in Green Hills, Sonic is eager to prove he has what it takes to be a true hero. His test comes when Dr. Robotnik returns, this time with a new partner, Knuckles, in search for an emerald that has the power to destroy civilizations. Sonic teams up with his own sidekick, Tails, and together they embark on a globe-trotting journey to find the emerald before it falls into the wrong hands.",
                   "popularity": 6587.056,
                   "poster_path": "/1j6JtMRAhdO3RaXRtiWdPL5D3SW.jpg",
                   "release_date": "2022-03-30",
                   "title": "Sonic the Hedgehog 2",
                   "video": false,
                   "vote_average": 7.7,
                   "vote_count": 197
                  },
                  {
                   "adult": false,
                   "backdrop_path": "/yzH5zvuEzzsHLZnn0jwYoPf0CMT.jpg",
                   "genre_ids": [
                    53,
                    28
                   ],
                   "id": 760926,
                   "original_language": "en",
                   "original_title": "Gold",
                   "overview": "In the not-too-distant future, two drifters traveling through the desert stumble across the biggest gold nugget ever found and the dream of immense wealth and greed takes hold. They hatch a plan to excavate their bounty, with one man leaving to secure the necessary tools while the other remains with the gold. The man who remains must endure harsh desert elements, ravenous wild dogs, and mysterious intruders, while battling the sinking suspicion that he has been abandoned to his fate.",
                   "popularity": 1228.945,
                   "poster_path": "/ejXBuNLvK4kZ7YcqeKqUWnCxdJq.jpg",
                   "release_date": "2022-01-13",
                   "title": "Gold",
                   "video": false,
                   "vote_average": 6.6,
                   "vote_count": 168
                  }],
                 "total_pages": 17,
                 "total_results": 321
                }
                """
        );
  }

  public static MockResponse nowPlayingResponse() {
      return new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody(
                      """
                       {
                        "dates": {
                         "maximum": "2022-05-02",
                         "minimum": "2022-04-15"
                        },
                        "page": 1,
                        "results": [
                         {
                          "adult": false,
                          "backdrop_path": "/egoyMDLqCxzjnSrWOz50uLlJWmD.jpg",
                          "genre_ids": [
                           28,
                           878,
                           35,
                           10751
                          ],
                          "id": 675353,
                          "original_language": "en",
                          "original_title": "Sonic the Hedgehog 2",
                          "overview": "After settling in Green Hills, Sonic is eager to prove he has what it takes to be a true hero. His test comes when Dr. Robotnik returns, this time with a new partner, Knuckles, in search for an emerald that has the power to destroy civilizations. Sonic teams up with his own sidekick, Tails, and together they embark on a globe-trotting journey to find the emerald before it falls into the wrong hands.",
                          "popularity": 6587.056,
                          "poster_path": "/1j6JtMRAhdO3RaXRtiWdPL5D3SW.jpg",
                          "release_date": "2022-03-30",
                          "title": "Sonic the Hedgehog 2",
                          "video": false,
                          "vote_average": 7.7,
                          "vote_count": 197
                         },
                         {
                          "adult": false,
                          "backdrop_path": "/yzH5zvuEzzsHLZnn0jwYoPf0CMT.jpg",
                          "genre_ids": [
                           53,
                           28
                          ],
                          "id": 760926,
                          "original_language": "en",
                          "original_title": "Gold",
                          "overview": "In the not-too-distant future, two drifters traveling through the desert stumble across the biggest gold nugget ever found and the dream of immense wealth and greed takes hold. They hatch a plan to excavate their bounty, with one man leaving to secure the necessary tools while the other remains with the gold. The man who remains must endure harsh desert elements, ravenous wild dogs, and mysterious intruders, while battling the sinking suspicion that he has been abandoned to his fate.",
                          "popularity": 1228.945,
                          "poster_path": "/ejXBuNLvK4kZ7YcqeKqUWnCxdJq.jpg",
                          "release_date": "2022-01-13",
                          "title": "Gold",
                          "video": false,
                          "vote_average": 6.6,
                          "vote_count": 168
                         }],
                        "total_pages": 17,
                        "total_results": 321
                       }
                       """
              );
    }

  public static MockResponse searchMoviesResponse() {
    return new MockResponse()
        .setResponseCode(200)
        .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
        .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
        .setBody(
                """
                {
                  "page": 38,
                  "results": [
                    {
                      "adult": false,
                      "backdrop_path": "/ckqqh8EjvVyaP23SBxv0ZsGZN51.jpg",
                      "genre_ids": [
                        99,
                        35
                      ],
                      "id": 541715,
                      "original_language": "en",
                      "original_title": "Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special",
                      "overview": "420 special of Action Bronson and friends getting high on the holiday watching his favorite show.",
                      "popularity": 0.6,
                      "poster_path": "/biSWYZENgrKztu8A5qa58GM3QUy.jpg",
                      "release_date": "2016-04-20",
                      "title": "Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special",
                      "video": false,
                      "vote_average": 6.8,
                      "vote_count": 4
                    },
                    {
                      "adult": false,
                      "backdrop_path": "/sQbyKuDwfOplT78peqIWcvssVkt.jpg",
                      "genre_ids": [
                        27,
                        878
                      ],
                      "id": 55952,
                      "original_language": "en",
                      "original_title": "Xtro 2: The Second Encounter",
                      "overview": "Scientists at a secret underground complex have found a way to travel to another dimension. Three dimension-travellers are the first to go through the gate - but are soon attacked by something that interrupts the communication with Earth. This horrible something uses the gate to travel back to the underground complex. Most of the staff are evacuated, except four heavily-armed militaries and Dr. Casserly and Dr. Summerfield who just can't stand each other.",
                      "popularity": 4.32,
                      "poster_path": "/n3x5eUOIem5hH2WKEVIsubpBUeK.jpg",
                      "release_date": "1990-05-04",
                      "title": "Xtro 2: The Second Encounter",
                      "video": false,
                      "vote_average": 3.2,
                      "vote_count": 18
                    },
                    {
                      "adult": false,
                      "backdrop_path": "/5MCYau94XLkvChn5NlyJEGDe3ml.jpg",
                      "genre_ids": [
                        53,
                        878,
                        28
                      ],
                      "id": 2787,
                      "original_language": "en",
                      "original_title": "Pitch Black",
                      "overview": "When their ship crash-lands on a remote planet, the marooned passengers soon learn that escaped convict Riddick isn't the only thing they have to fear. Deadly creatures lurk in the shadows, waiting to attack in the dark, and the planet is rapidly plunging into the utter blackness of a total eclipse. With the body count rising, the doomed survivors are forced to turn to Riddick with his eerie eyes to guide them through the darkness to safety. With time running out, there's only one rule: Stay in the light.",
                      "popularity": 10.571,
                      "poster_path": "/3AnlxZ5CZnhKKzjgFyY6EHxmOyl.jpg",
                      "release_date": "2000-02-18",
                      "title": "Pitch Black",
                      "video": false,
                      "vote_average": 6.8,
                      "vote_count": 3478
                    }
                  ],
                  "total_pages": 38,
                  "total_results": 744
                }
                """
        );

  }

  public static MockResponse creditsResponse() {
    return new MockResponse()
        .setResponseCode(200)
        .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
        .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
        .setBody(
                """
                {
                  "id": 597,
                  "cast": [
                    {
                      "adult": false,
                      "gender": 2,
                      "id": 6193,
                      "known_for_department": "Acting",
                      "name": "Leonardo DiCaprio",
                      "original_name": "Leonardo DiCaprio",
                      "popularity": 28.19,
                      "profile_path": "/poster6193.jpg",
                      "cast_id": 21,
                      "character": "Jack Dawson 1",
                      "credit_id": "52fe425ac3a36847f80179cf",
                      "order": 0
                    },
                    {
                      "adult": false,
                      "gender": 2,
                      "id": 9999,
                      "known_for_department": "Acting",
                      "name": "Leonardo DiCaprio",
                      "original_name": "Leonardo DiCaprio",
                      "popularity": 28.19,
                      "profile_path": "/poster9999.jpg",
                      "cast_id": 21,
                      "character": "Jack Dawson 2",
                      "credit_id": "52fe425ac3a36847f80179cf",
                      "order": 1
                    },
                    {
                      "adult": false,
                      "gender": 1,
                      "id": 204,
                      "known_for_department": "Acting",
                      "name": "Kate Winslet",
                      "original_name": "Kate Winslet",
                      "popularity": 8.066,
                      "profile_path": "/poster204.jpg",
                      "cast_id": 20,
                      "character": "Rose DeWitt Bukater",
                      "credit_id": "52fe425ac3a36847f80179cb",
                      "order": 2
                    },
                    {
                      "adult": false,
                      "gender": 2,
                      "id": 6193,
                      "known_for_department": "Acting",
                      "name": "Leonardo DiCaprio",
                      "original_name": "Leonardo DiCaprio",
                      "popularity": 28.19,
                      "profile_path": "/poster6193.jpg",
                      "cast_id": 21,
                      "character": "Jack Dawson",
                      "credit_id": "52fe425ac3a36847f80179cf",
                      "order": 50
                    },
                    {
                      "adult": false,
                      "gender": 2,
                      "id": 1954,
                      "known_for_department": "Acting",
                      "name": "Billy Zane Zune Zone",
                      "original_name": "Billy",
                      "popularity": 15.4,
                      "profile_path": "/poster1954.jpg",
                      "cast_id": 23,
                      "character": "Cal Hockley",
                      "credit_id": "52fe425ac3a36847f80179d7",
                      "order": 10
                    }
                  ],
                  "crew": [
                    {
                      "adult": false,
                      "gender": 2,
                      "id": 2216,
                      "known_for_department": "Sound",
                      "name": "Gary Rydstrom",
                      "original_name": "Gary Rydstrom",
                      "popularity": 0.98,
                      "profile_path": "/1DoKaxoJlz6hV9bai43e07GxGQf.jpg",
                      "credit_id": "5a71e3709251417f2b00954c",
                      "department": "Sound",
                      "job": "Sound Re-Recording Mixer"
                    },
                    {
                      "adult": false,
                      "gender": 2,
                      "id": 2710,
                      "known_for_department": "Directing",
                      "name": "James Cameron",
                      "original_name": "James Cameron",
                      "popularity": 4.238,
                      "profile_path": "/poster2710.jpg",
                      "credit_id": "52fe425ac3a36847f8017961",
                      "department": "Writing",
                      "job": "Director"
                    },
                    {
                      "adult": false,
                      "gender": 2,
                      "id": 7890,
                      "known_for_department": "Directing",
                      "name": "James Cameron Number Two",
                      "original_name": "James Cameron",
                      "popularity": 4.238,
                      "profile_path": "/poster7890.jpg",
                      "credit_id": "52fe425ac3a36847f801795b",
                      "department": "Directing",
                      "job": "Director"
                    },
                    {
                      "adult": false,
                      "gender": 2,
                      "id": 2710,
                      "known_for_department": "Directing",
                      "name": "James Cameron",
                      "original_name": "James Cameron",
                      "popularity": 4.238,
                      "profile_path": "/poster2710.jpg",
                      "credit_id": "52fe425ac3a36847f8017961",
                      "department": "Writing",
                      "job": "Director"
                    },
                    {
                      "adult": false,
                      "gender": 2,
                      "id": 1000,
                      "known_for_department": "Directing",
                      "name": "   James   ",
                      "original_name": "James Cameron",
                      "popularity": 4.238,
                      "profile_path": "/poster1000.jpg",
                      "credit_id": "52fe425ac3a36847f801795b",
                      "department": "Directing",
                      "job": "Director"
                    }
                  ]
                }
                """
        );
  }

  public static MockResponse directorMovieCreditsResponse() {
      return new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody(
                      """
                      {
                          "cast": [
                                {
                                    "adult": false,
                                    "backdrop_path": null,
                                    "genre_ids": [
                                        27,
                                        99,
                                        28
                                    ],
                                    "id": 376042,
                                    "original_language": "es",
                                    "original_title": "Katanas, yakuzas y cintas de video",
                                    "overview": "",
                                    "popularity": 0.961,
                                    "poster_path": "/hSKXKTR3chJgUBP8fiUVUnur7pW.jpg",
                                    "release_date": "2004-11-11",
                                    "title": "Katanas, yakuzas y cintas de video",
                                    "video": false,
                                    "vote_average": 0.0,
                                    "vote_count": 0,
                                    "character": "(archive footage)",
                                    "credit_id": "568b936ac3a36860e902e134",
                                    "order": 0
                                },
                                {
                                    "adult": false,
                                    "backdrop_path": "/w2asPC7prjaMnCwt7yK8JvLp3uS.jpg",
                                    "genre_ids": [
                                        99
                                    ],
                                    "id": 427185,
                                    "original_language": "ko",
                                    "original_title": "올드 데이즈",
                                    "overview": "Old Days is a documentary about Park Chan-wook's original masterpiece, Oldboy. It was created for Plain Archive's Blu-ray release and first screened at the Jeonju International Film Festival.",
                                    "popularity": 1.358,
                                    "poster_path": "/eviGVU7GGZlP4hGVkKAOe75Bemq.jpg",
                                    "release_date": "2016-04-29",
                                    "title": "Old Days",
                                    "video": false,
                                    "vote_average": 6.6,
                                    "vote_count": 5,
                                    "character": "Self",
                                    "credit_id": "58343bc59251416bc6003bb0",
                                    "order": 0
                                }
                          ],
                          "crew": [
                                {
                                    "adult": false,
                                    "backdrop_path": "/uiXmFezM5Xg1RWEIRxX8gjIUiWW.jpg",
                                    "genre_ids": [
                                        10752,
                                        18,
                                        53,
                                        28
                                    ],
                                    "id": 2440,
                                    "original_language": "ko",
                                    "original_title": "공동경비구역 JSA",
                                    "overview": "Two North Korean soldiers are killed in the border area between North and South Korea, prompting an investigation by a neutral body. The sergeant is the shooter, but the lead investigator, a Swiss-Korean woman, receives differing accounts from the two sides.",
                                    "popularity": 17.83,
                                    "poster_path": "/etoPOj0bXzfw0LBNslCxqO7MHuv.jpg",
                                    "release_date": "2000-09-09",
                                    "title": "Joint Security Area",
                                    "video": false,
                                    "vote_average": 7.835,
                                    "vote_count": 501,
                                    "credit_id": "52fe4357c3a36847f804d057",
                                    "department": "Directing",
                                    "job": "Director"
                                },
                                {
                                    "adult": false,
                                    "backdrop_path": "/wP1nu98mgiSOyuPmMWdZ8109S9j.jpg",
                                    "genre_ids": [
                                        18,
                                        53
                                    ],
                                    "id": 4550,
                                    "original_language": "ko",
                                    "original_title": "친절한 금자씨",
                                    "overview": "After being wrongfully convicted, a woman is imprisoned for 13 years and forced to give up her daughter. While in prison she gains the respect of her cellmates and plots her revenge on the man responsible. Once released, she begins her elaborate plan of retribution, but discovers a horrifying truth.",
                                    "popularity": 16.175,
                                    "poster_path": "/pxgjBSwsa2IBSJoVrsLT6qxOO6N.jpg",
                                    "release_date": "2005-07-29",
                                    "title": "Sympathy for Lady Vengeance",
                                    "video": false,
                                    "vote_average": 7.548,
                                    "vote_count": 1295,
                                    "credit_id": "52fe43cbc3a36847f8070265",
                                    "department": "Directing",
                                    "job": "Director"
                                },
                                {
                                    "adult": false,
                                    "backdrop_path": "/6WMeEmwXWKwrWBd6uDXjtWPigzM.jpg",
                                    "genre_ids": [
                                        28,
                                        18,
                                        53
                                    ],
                                    "id": 4689,
                                    "original_language": "ko",
                                    "original_title": "복수는 나의 것",
                                    "overview": "A deaf man and his girlfriend resort to desperate measures in order to fund a kidney transplant for his sister. Things go horribly wrong, and the situation spirals rapidly into a cycle of violence and revenge.",
                                    "popularity": 12.212,
                                    "poster_path": "/qtB1B1KcmggRfuhZELQ08aIGBV1.jpg",
                                    "release_date": "2002-03-29",
                                    "title": "Sympathy for Mr. Vengeance",
                                    "video": false,
                                    "vote_average": 7.472,
                                    "vote_count": 1119,
                                    "credit_id": "52fe43d3c3a36847f80729a5",
                                    "department": "Directing",
                                    "job": "Director"
                                },
                                {
                                    "adult": false,
                                    "backdrop_path": "/883WnrlBcwNaskVnfKDj3c9Xehc.jpg",
                                    "genre_ids": [
                                        18
                                    ],
                                    "id": 79392,
                                    "original_language": "ko",
                                    "original_title": "파란만장",
                                    "overview": "A man casually sets up for a fishing trip at the water's edge. Evening comes and a tug on his line presents him with the body of a woman.",
                                    "popularity": 1.942,
                                    "poster_path": "/8ToDjbLFpuskHfGukOBfxTFj1xX.jpg",
                                    "release_date": "2011-01-27",
                                    "title": "Night Fishing",
                                    "video": false,
                                    "vote_average": 5.6,
                                    "vote_count": 32,
                                    "credit_id": "52fe49d0c3a368484e1415c1",
                                    "department": "Writing",
                                    "job": "Writer"
                                }
                          ],
                          "id": 6
                      }
                      """
              );
  }

  public static MockResponse movieResponse() {
    return new MockResponse()
        .setResponseCode(200)
        .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
        .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
        .setBody(
                """
                {
                  "adult": false,
                  "backdrop_path": "/AmR3JG1VQVxU8TfAvljUhfSFUOx.jpg",
                  "belongs_to_collection": {
                    "id": 8091,
                    "name": "Alien Collection",
                    "poster_path": "/iVzIeC3PbG9BtDAudpwSNdKAgh6.jpg",
                    "backdrop_path": "/kB0Y3uGe9ohJa59Lk8UO9cUOxGM.jpg"
                  },
                  "budget": 11000000,
                  "genres": [
                    {
                      "id": 27,
                      "name": "Horror"
                    },
                    {
                      "id": 878,
                      "name": "Science Fiction"
                    }
                  ],
                  "homepage": "https://www.20thcenturystudios.com/movies/alien",
                  "id": 348,
                  "imdb_id": "tt0078748",
                  "original_language": "en",
                  "original_title": "Alien",
                  "overview": "During its return to the earth, commercial spaceship Nostromo intercepts a distress signal from a distant planet. When a three-member team of the crew discovers a chamber containing thousands of eggs on the planet, a creature inside one of the eggs attacks an explorer. The entire crew is unaware of the impending nightmare set to descend upon them when the alien parasite planted inside its unfortunate host is birthed.",
                  "popularity": 48.046,
                  "poster_path": "/vfrQk5IPloGg1v9Rzbh2Eg3VGyM.jpg",
                  "production_companies": [
                    {
                      "id": 19747,
                      "logo_path": null,
                      "name": "Brandywine Productions",
                      "origin_country": "US"
                    },
                    {
                      "id": 25,
                      "logo_path": "/qZCc1lty5FzX30aOCVRBLzaVmcp.png",
                      "name": "20th Century Fox",
                      "origin_country": "US"
                    }
                  ],
                  "production_countries": [
                    {
                      "iso_3166_1": "US",
                      "name": "United States of America"
                    },
                    {
                      "iso_3166_1": "GB",
                      "name": "United Kingdom"
                    }
                  ],
                  "release_date": "1979-05-25",
                  "revenue": 104931801,
                  "runtime": 117,
                  "spoken_languages": [
                    {
                      "english_name": "English",
                      "iso_639_1": "en",
                      "name": "English"
                    },
                    {
                      "english_name": "Spanish",
                      "iso_639_1": "es",
                      "name": "Español"
                    }
                  ],
                  "status": "Released",
                  "tagline": "In space no one can hear you scream.",
                  "title": "Alien",
                  "video": false,
                  "vote_average": 8.1,
                  "vote_count": 11018
                }
                """
        );
  }
}
