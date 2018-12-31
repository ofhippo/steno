package steno;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static steno.Schemes.*;

public class KeyerTest {
    private static final String SHORT_TEXT = "Yes, I like to read stories by the science fiction author Isaac Asimov!";
    private static final String SHORT_TEXT_HOMOPHONE = "I need to buy a clarinet reed";
    private static final String SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT = "The reeds blow in the wind";
    private static final String HOLMES_LONG_TEXT = "He disappeared into his bedroom and returned in a few minutes in the character of an amiable and simple-minded Nonconformist clergyman. His broad black hat, his baggy trousers, his white tie, his sympathetic smile, and general look of peering and benevolent curiosity were such as Mr. John Hare alone could have equalled. It was not merely that Holmes changed his costume. His expression, his manner, his very soul seemed to vary with every fresh part that he assumed. The stage lost a fine actor, even as science lost an acute reasoner, when he became a specialist in crime. It was a quarter past six when we left Baker Street, and it still wanted ten minutes to the hour when we found ourselves in Serpentine Avenue. It was already dusk, and the lamps were just being lighted as we paced up and down in front of Briony Lodge, waiting for the coming of its occupant. The house was just such as I had pictured it from Sherlock Holmes' succinct description, but the locality appeared to be less private than I expected. On the contrary, for a small street in a quiet neighbourhood, it was remarkably animated. There was a group of shabbily dressed men smoking and laughing in a corner, a scissors-grinder with his wheel, two guardsmen who were flirting with a nurse-girl, and several well-dressed young men who were lounging up and down with cigars in their mouths.";
    private static final String RED_RISING_LONG_TEXT = "The first thing you should know about me is I am my father’s son. And when they came for him, I did as he asked. I did not cry. Not when the Society televised the arrest. Not when the Golds tried him. Not when the Grays hanged him. Mother hit me for that. My brother Kieran was supposed to be the stoic one. He was the elder, I the younger. I was supposed to cry. Instead, Kieran bawled like a girl when Little Eo tucked a haemanthus into Father’s left workboot and ran back to her own father’s side. My sister Leanna murmured a lament beside me. I just watched and thought it a shame that he died dancing but without his dancing shoes. On Mars there is not much gravity. So you have to pull the feet to break the neck. They let the loved ones do it. I smell my own stink inside my frysuit. The suit is some kind of nanoplastic and is hot as its name suggests. It insulates me toe to head. Nothing gets in. Nothing gets out. Especially not the heat. Worst part is you can’t wipe the sweat from your eyes. Bloodydamn stings as it goes through the headband to puddle at the heels. Not to mention the stink when you piss. Which you always do. Gotta take in a load of water through the drinktube. I guess you could be fit with a catheter. We choose the stink. The drillers of my clan chatter some gossip over the comm in my ear as I ride atop the clawDrill. I’m alone in this deep tunnel on a machine built like a titanic metal hand, one that grasps and gnaws at the ground. I control its rockmelting digits from the holster seat atop the drill, just where the elbow joint would be. There, my fingers fit into control gloves that manipulate the many tentacle-like drills some ninety meters below my perch. To be a Helldiver, they say your fingers must flicker fast as tongues of fire. Mine flicker faster. Despite the voices in my ear, I am alone in the deep tunnel. My existence is vibration, the echo of my own breath, and heat so thick and noxious it feels like I’m swaddled in a heavy quilt of hot piss. A new river of sweat breaks through the scarlet sweatband tied around my forehead and slips into my eyes, burning them till they’re as red as my rusty hair. I used to reach and try to wipe the sweat away, only to scratch futilely at the faceplate of my frysuit. I still want to. Even after three years, the tickle and sting of the sweat is a raw misery. The tunnel walls around my holster seat are bathed a sulfurous yellow by a corona of lights. The reach of the light fades as I look up the thin vertical shaft I’ve carved today. Above, precious helium-3 glimmers like liquid silver, but I’m looking at the shadows, looking for the pitvipers that curl through the darkness seeking the warmth of my drill. They’ll eat into your suit too, bite through the shell and then try to burrow into the warmest place they find, usually your belly, so they can lay their eggs. I’ve been bitten before. Still dream of the beast—black, like a thick tendril of oil. They can get as wide as a thigh and long as three men, but it’s the babies we fear. They don’t know how to ration their poison. Like me, their ancestors came from Earth, then Mars and the deep tunnels changed them. It is eerie in the deep tunnels. Lonely. Beyond the roar of the drill, I hear the voices of my friends, all older. But I cannot see them a half klick above me in the darkness. They drill high above, near the mouth of the tunnel that I’ve carved, descending with hooks and lines to dangle along the sides of the tunnel to get at the small veins of helium-3. They mine with meter-long drills, gobbling up the chaff. The work still requires mad dexterity of foot and hand, but I’m the earner in this crew. I am the Helldiver. It takes a certain kind—and I’m the youngest anyone can remember. I’ve been in the mines for three years. You start at thirteen. Old enough to screw, old enough to crew. At least that’s what Uncle Narol said. Except I didn’t get married till six months back, so I don’t know why he said it. Eo dances through my thoughts as I peer into my control display and slip the clawDrill’s fingers around a fresh vein. Eo. Sometimes it’s difficult to think of her as anything but what we used to call her as children. Little Eo—a tiny girl hidden beneath a mane of red. Red like the rock around me, not true red, rust-red. Red like our home, like Mars. Eo is sixteen too. And she may be like me—from a clan of Red earth diggers, a clan of song and dance and soil—but she could be made from air, from the ether that binds the stars in a patchwork. Not that I’ve ever seen stars. No Red from the mining colonies sees the stars. Little Eo. They wanted to marry her off when she turned fourteen, like all girls of the clans. But she took the short rations and waited for me to reach sixteen, wedAge for men, before slipping that cord around her finger. She said she knew we’d marry since we were children. I didn’t. “Hold. Hold. Hold!” Uncle Narol snaps over the comm channel. “Darrow, hold, boy!” My fingers freeze. He’s high above with the rest of them, watching my progress on his head unit. “What’s the burn?” I ask, annoyed. I don’t like being interrupted. “What’s the burn, the little Helldiver asks.” Old Barlow chuckles. “Gas pocket, that’s what,” Narol snaps. He’s the headTalk for our two-hundred-plus crew. “Hold. Calling a scanCrew to check the particulars before you blow us all to hell.” “That gas pocket? It’s a tiny one,” I say. “More like a gas pimple. I can manage it.” “A year on the drill and he thinks he knows his head from his hole! Poor little pissant,” old Barlow adds dryly. “Remember the words of our golden leader. Patience and obedience, young one. Patience is the better part of valor. And obedience the better part of humanity. Listen to your elders.” I roll my eyes at the epigram. If the elders could do what I can, maybe listening would have its merits. But they are slow in hand and mind. Sometimes I feel like they want me to be just the same, especially my uncle. “I’m on a tear,” I say. “If you think there’s a gas pocket, I can just hop down and handscan it. Easy. No dilldally.” They’ll preach caution. As if caution has ever helped them. We haven’t won a Laurel in ages. “Want to make Eo a widow?” Barlow laughs, voice crackling with static. “Okay by me. She is a pretty little thing. Drill into that pocket and leave her to me. Old and fat I be, but my drill still digs a dent.” A chorus of laughter comes from the two hundred drillers above. My knuckles turn white as I grip the controls. “Listen to Uncle Narol, Darrow. Better to back off till we can get a reading,” my brother Kieran adds. He’s three years older. Makes him think he’s a sage, that he knows more. He just knows caution. “There’ll be time.” “Time? Hell, it’ll take hours,” I snap. They’re all against me in this. They’re all wrong and slow and don’t understand that the Laurel is only a bold move away. More, they doubt me. “You are being a coward, Narol.” Silence on the other end of the line. Calling a man a coward—not a good way to get his cooperation. Shouldn’t have said it. “I say make the scan yourself,” Loran, my cousin and Narol’s son, squawks. “Don’t and Gamma is good as Gold—they’ll get the Laurel for, oh, the hundredth time.” The Laurel. Twenty-four clans in the underground mining colony of Lykos, one Laurel per quarter. It means more food than you can eat. It means more burners to smoke. Imported quilts from Earth. Amber swill with the Society’s quality markings. It means winning. Gamma clan has had it since anyone can remember. So it’s always been about the Quota for us lesser clans, just enough to scrape by. Eo says the Laurel is the carrot the Society dangles, always just far enough beyond our grasp. Just enough so we know how short we really are and how little we can do about it. We’re supposed to be pioneers. Eo calls us slaves. I just think we never try hard enough. Never take the big risks because of the old men. “Loran, shut up about the Laurel. Hit the gas and we’ll miss all the bloodydamn Laurels to kingdom come, boy,” Uncle Narol growls. He’s slurring. I can practically smell the drink through the comm. He wants to call a sensor team to cover his own ass. Or he’s scared. The drunk was born pissing himself out of fear. Fear of what? Our overlords, the Golds? Their minions, the Grays? Who knows? Few people. Who cares? Even fewer. Actually, just one man cared for my uncle, and he died when my uncle pulled his feet. My uncle is weak. He is cautious and immoderate in his drink, a pale shadow of my father. His blinks are long and hard, as though it pains him to open his eyes each time and see the world again. I don’t trust him down here in the mines, or anywhere for that matter. But my mother would tell me to listen to him; she would remind me to respect my elders. Even though I am wed, even though I am the Helldiver of my clan, she would say that my “blisters have not yet become calluses.” I will obey, even though it is as maddening as the tickle of the sweat on my face. “Fine,” I murmur. I clench the drill fist and wait as my uncle calls it in from the safety of the chamber above the deep tunnel. This will take hours. I do the math. Eight hours till whistle call. To beat Gamma, I’ve got to keep a rate of 156.5 kilos an hour. It’ll take two and a half hours for the scanCrew to get here and do their deal, at best. So I’ve got to pump out 227.6 kilos per hour after that. Impossible. But if I keep going and squab the tedious scan, it’s ours. I wonder if Uncle Narol and Barlow know how close we are. Probably. Probably just don’t think anything is ever worth the risk. Probably think divine intervention will squab our chances. Gamma has the Laurel. That’s the way things are and will ever be. We of Lambda just try to scrape by on our foodstuffs and meager comforts. No rising. No falling. Nothing is worth the risk of changing the hierarchy. My father found that out at the end of a rope. Nothing is worth risking death. Against my chest, I feel the wedding band of hair and silk dangling from the cord around my neck and think of Eo’s ribs. I’ll see a few more of the slender things through her skin this month. She’ll go asking the Gamma families for scraps behind my back. I’ll act like I don’t know. But we’ll still be hungry. I eat too much because I’m sixteen and still growing tall; Eo lies and says she’s never got much of an appetite. Some women sell themselves for food or luxuries to the Tinpots (Grays, to be technic about it), the Society’s garrison troops of our little mining colony. She wouldn’t sell her body to feed me. Would she? But then I think about it. I’d do anything to feed her . . . I look down over the edge of my drill. It’s a long fall to the bottom of the hole I’ve dug. Nothing but molten rock and hissing drills. But before I know what’s what, I’m out of my straps, scanner in hand and jumping down the hundred-meter drop toward the drill fingers. I kick back and forth between the vertical mineshaft’s walls and the drill’s long, vibrating body to slow my fall. I make sure I’m not near a pitviper nest when I throw out an arm to catch myself on a gear just above the drill fingers. The ten drills glow with heat. The air shimmers and distorts. I feel the heat on my face, feel it stabbing my eyes, feel it ache in my belly and balls. Those drills will melt your bones if you’re not careful. And I’m not careful. Just nimble. I lower myself hand over hand, going feetfirst between the drill fingers so that I can lower the scanner close enough to the gas pocket to get a reading. This was a mistake. Voices shout at me through the comm. I almost brush one of the drills as I finally lower myself close enough to the gas pocket. The scanner flickers in my hand as it takes its reading. My suit is bubbling and I smell something sweet and sharp, like burned syrup. To a Helldiver, it is the smell of death.";
    private static final String BLOG_POST = "Many people seem to think artificial intelligence is leading us toward some kind of robo-apocalypse or a dreamscape techno-utopia. As a New Relic software engineer working on AI projects, I’m invested in—and entertained by—those fears and expectations for how AI may affect our society. I don’t believe we’ll be in either scenario anytime soon. But in between those extreme views lie all the real-world ways AI already enhances our lives. For example, AI has blessed us by catching 99.9% of Gmail spam in our inboxes for years now. A pilot project with smart traffic lights powered by AI is reducing vehicle wait time by 40%. As more and more industries experiment with AI, its impact on our day-to-day lives will continue to grow. So where exactly are we with AI, and where are we headed? AI in the name of science and industry  While our future relationship with artificial intelligence remains uncertain, we can ground our thinking by looking at the current state of AI affairs. For example, recently I attended the International Conference for Learning Representations (ICLR), where I gleaned some intriguing hints on what might come next in AI. The conference, in Vancouver, British Columbia, has grown exponentially in recent years, both in number of attendees and number of papers submitted, so it isn’t surprising to learn that researchers continue to pour efforts into making machines smarter. In paper titled Zero-Shot Visual Imitation, researchers describe a technique they developed that enabled a robot to tie a knot and navigate an office after a single demonstration, with no specific guidance. And in a presentation on integer deep neural network training, attendees saw how an autonomous bicycle can follow people on its own. In addition to these examples illustrating AI’s progress, the conference also revealed that standard approaches to AI have some serious flaws. In fact, one presentation argued that 90% of surveyed machine learning researchers agree there is at least a slight reproducibility crisis in the field. And even as AI continues to evolve, humans clearly still hold the upper hand in many arenas. Researchers at LabSix, for example, printed a 3D model of a turtle and fooled a computer into thinking it was a rifle. Nevertheless, while machines may still lack “general intelligence,” they are getting smart and capable enough to seriously impact our society, especially by influencing the economy. In an article on the What can machine learning do? Workforce implications, researchers from MIT and The National Bureau of Economic Research say that while we can expect more changes to automation, there likely won’t be a widespread replacement of all human workers anytime soon. “Machines cannot do the full range of tasks that humans can do,” they state. Machines have a competence that is “dramatically narrower and more fragile than human decision making.” The research could apply to digital performance monitoring  While many of the advances presented at the ICLR conference aren’t necessarily relevant to the digital performance monitoring and management space, a couple of the ideas are relevant. Consider experiments in healthcare from researchers at Johns Hopkins University using time series data to determine the risk of death for pneumonia patients. Could the same AI strategies used to keep patients alive help DevOps teams keep apps and hosts running smoothly? Despite huge differences between the use cases, I think it’s a real possibility. Machine learning cares more about the shape of data than the specific content. The technique used in the Johns Hopkins research works on generic time series data (of which New Relic customers gather an abundance) and doesn’t care if the data is about people or machines. Or what about a new AI technology that learns to understand data well enough to fill in gaps where data is missing and sharpen regions where data is fuzzy in data samples used to generate models of complex distributions? Such tech could help us with anomaly detection or estimating data in monitoring blind spots. What does this mean for New Relic’s AI journey?  Monitoring solutions have been collecting and visualizing software data increasingly well for more than a decade now, but as systems become more complex, the old techniques can’t always keep up. Tools like New Relic can now collect data simultaneously across hundreds of interconnected hosts and microservices. With that level of complexity, it’s no longer enough to simply throw data on a screen and leave it up to the user to sift through it to find meaning and determine the proper action. When our customers experience incidents in their software, it can cost them a lot of time, effort, and potential lost profits. These high stakes require something more from monitoring vendors. Our customers need smart systems that continually analyze and search for anomalous behavior, so that they can make predictions and sort data into dynamic groups. That’s the magic consumers will soon expect from AI-driven technology. If our machines could take on the duty of analysis, we could hand things off to our users at the last possible moment, when only human expertise and decision-making can suffice, maximizing the efficiency of our users. New Relic has already made progress on this front, and we’ve learned a lot from our first steps with AI. We’ve built tools into our ecosystem that can tell whether or not things are behaving normally. Error profiles, for instance, use statistical measures to surface the errors that deviate most dramatically from the non-error transactions in your app. The profiles provide visual details about differences in the frequency of values for the events, showing you where to focus your attention without making you manually click through all the dimensions. Dynamic baseline alerting allows our users to set alert thresholds for a particular application metric based on a predictive baseline for that metric. We’re still working toward the reliable execution and prediction accuracy necessary to create out-of-the-box global views of the current and future health of complex systems. We’re working on some promising prototypes and are focused on advancing the future of AIOps. Using a data-driven approach to AI, we take steps to validate our thinking with customers at each step along the way. We’re continuing to evolve our digital performance monitoring platform to drastically improve the effectiveness of our users. We invite you to keep an eye on our progress, and brace for an exciting future!  \n";
    public static final int NUM_TRIALS_FOR_RANDOM = 100;

    @Test
    public void scoreDictionaryWithoutContext() {
        Keyer keyer = new Keyer(new ArpabetCompressor(IDENTITY));
        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(0.5, 1.0);

        keyer = new Keyer(new ArpabetCompressor(COLLAPSED_VOWELS));
        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(1.0, 1.5);

        keyer = new Keyer(new ArpabetCompressor(DUMB_13_STATE));
        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(1.5, 2.0);

        keyer = new Keyer(new ArpabetCompressor(DUMB_8_STATE));
        assertThat(keyer.scoreDictionaryWithoutContext().cost()).isBetween(2.0, 2.5);
    }

    @Test
    public void scoreText() {
        Keyer keyer = new Keyer(new ArpabetCompressor(IDENTITY));
        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isEqualTo(0);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isEqualTo(0);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isEqualTo(0);
        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(0.5);

        keyer = new Keyer(new ArpabetCompressor(COLLAPSED_VOWELS));
        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(1);

        keyer = new Keyer(new ArpabetCompressor(DUMB_13_STATE));
        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isLessThan(1.5);
        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(1d);
        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(1.5);
        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(1);

        keyer = new Keyer(new ArpabetCompressor(DUMB_8_STATE));
        assertThat(keyer.scoreText(SHORT_TEXT).cost()).isLessThan(1.1);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE).cost()).isLessThan(1.5);
        assertThat(keyer.scoreText(SHORT_TEXT_HOMOPHONE_LITTLE_CONTEXT).cost()).isLessThan(2.1);
        assertThat(keyer.scoreText(RED_RISING_LONG_TEXT).cost()).isLessThan(2.0);
        assertThat(keyer.scoreText(HOLMES_LONG_TEXT).cost()).isLessThan(2.0);
        assertThat(keyer.scoreText(BLOG_POST).cost()).isLessThan(1.5);

        runTrialsAndPrintStats(Arpabet.values().length);
        for (int range = 15; range >= 2; range--) {
            runTrialsAndPrintStats(range);
        }
    }

    private void runTrialsAndPrintStats(int range) {
        Keyer keyer;
        double minCost = 1e9;
        double sumCost = 0;
        double minCostPerfect = 0;
        double sumCostPerfect = 0;
        double minCostGood = 0;
        double sumCostGood = 0;
        double minCostOK = 0;
        double sumCostOK = 0;
        for (int i = 0; i < NUM_TRIALS_FOR_RANDOM; i++) {
            keyer = new Keyer(new ArpabetCompressor(Schemes.random(range)));
            final PerformanceStats stats = keyer.scoreText(BLOG_POST);
            final double cost = stats.cost();
            final double perfect = stats.getPercentageWithRankAtOrBelow(0);
            final double good = stats.getPercentageWithRankAtOrBelow(2);
            final double ok = stats.getPercentageWithRankAtOrBelow(8);

            sumCost += cost;
            sumCostPerfect += perfect;
            sumCostGood += good;
            sumCostOK += ok;

            if (cost < minCost) {
                minCost = cost;
                minCostPerfect = perfect;
                minCostGood = good;
                minCostOK = ok;
            }
        }
        System.out.println("---range" + String.valueOf(range) + "----");
        System.out.println("min cost: " + String.valueOf(minCost));
        System.out.println("avg cost: " + String.valueOf(sumCost / NUM_TRIALS_FOR_RANDOM));
        System.out.println("min cost perfect %: " + String.valueOf(minCostPerfect));
        System.out.println("avg cost perfect %: " + String.valueOf(sumCostPerfect / NUM_TRIALS_FOR_RANDOM));
        System.out.println("min cost good %: " + String.valueOf(minCostGood));
        System.out.println("avg cost good %: " + String.valueOf(sumCostGood / NUM_TRIALS_FOR_RANDOM));
        System.out.println("min cost ok %: " + String.valueOf(minCostOK));
        System.out.println("avg cost ok %: " + String.valueOf(sumCostOK / NUM_TRIALS_FOR_RANDOM));
        System.out.println();
    }
}