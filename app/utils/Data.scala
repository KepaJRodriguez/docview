package utils

object Data {

  /**
   * Script mappings for ISO15924. NB: These are not localised.
   *
   * Gleaned from: http://unicode.org/iso15924/iso15924-codes.html
   * (with long items slightly truncated for display.)
   */
  val scripts = List(
    "Afak" -> "Afaka",
    "Aghb" -> "Caucasian Albanian",
    "Arab" -> "Arabic",
    "Armi" -> "Imperial Aramaic",
    "Armn" -> "Armenian",
    "Avst" -> "Avestan",
    "Bali" -> "Balinese",
    "Bamu" -> "Bamum",
    "Bass" -> "Bassa Vah",
    "Batk" -> "Batak",
    "Beng" -> "Bengali",
    "Blis" -> "Blissymbols",
    "Bopo" -> "Bopomofo",
    "Brah" -> "Brahmi",
    "Brai" -> "Braille",
    "Bugi" -> "Buginese",
    "Buhd" -> "Buhid",
    "Cakm" -> "Chakma",
    "Cans" -> "Unified Canadian Aboriginal Syllabics",
    "Cari" -> "Carian",
    "Cham" -> "Cham",
    "Cher" -> "Cherokee",
    "Cirt" -> "Cirth",
    "Copt" -> "Coptic",
    "Cprt" -> "Cypriot",
    "Cyrl" -> "Cyrillic",
    "Cyrs" -> "Cyrillic (Old Church Slavonic variant)",
    "Deva" -> "Devanagari (Nagari)",
    "Dsrt" -> "Deseret (Mormon)",
    "Dupl" -> "Duployan shorthand",
    "Egyd" -> "Egyptian demotic",
    "Egyh" -> "Egyptian hieratic",
    "Egyp" -> "Egyptian hieroglyphs",
    "Elba" -> "Elbasan",
    "Ethi" -> "Ethiopic (Geʻez)",
    "Geok" -> "Khutsuri (Asomtavruli and Nuskhuri)",
    "Geor" -> "Georgian (Mkhedruli)",
    "Glag" -> "Glagolitic",
    "Goth" -> "Gothic",
    "Gran" -> "Grantha",
    "Grek" -> "Greek",
    "Gujr" -> "Gujarati",
    "Guru" -> "Gurmukhi",
    "Hang" -> "Hangul (Hangŭl, Hangeul)",
    "Hani" -> "Han (Hanzi, Kanji, Hanja)",
    "Hano" -> "Hanunoo (Hanunóo)",
    "Hans" -> "Han (Simplified variant)",
    "Hant" -> "Han (Traditional variant)",
    "Hebr" -> "Hebrew",
    "Hira" -> "Hiragana",
    "Hluw" -> "Anatolian Hieroglyphs",
    "Hmng" -> "Pahawh Hmong",
    "Hrkt" -> "Japanese syllabaries",
    "Hung" -> "Old Hungarian (Hungarian Runic)",
    "Inds" -> "Indus (Harappan)",
    "Ital" -> "Old Italic",
    "Java" -> "Javanese",
    "Jpan" -> "Japanese",
    "Jurc" -> "Jurchen",
    "Kali" -> "Kayah Li",
    "Kana" -> "Katakana",
    "Khar" -> "Kharoshthi",
    "Khmr" -> "Khmer",
    "Khoj" -> "Khojki",
    "Knda" -> "Kannada",
    "Kore" -> "Korean (alias for Hangul + Han)",
    "Kpel" -> "Kpelle",
    "Kthi" -> "Kaithi",
    "Lana" -> "Tai Tham (Lanna)",
    "Laoo" -> "Lao",
    "Latf" -> "Latin (Fraktur variant)",
    "Latg" -> "Latin (Gaelic variant)",
    "Latn" -> "Latin",
    "Lepc" -> "Lepcha (Róng)",
    "Limb" -> "Limbu",
    "Lina" -> "Linear A",
    "Linb" -> "Linear B",
    "Lisu" -> "Lisu (Fraser)",
    "Loma" -> "Loma",
    "Lyci" -> "Lycian",
    "Lydi" -> "Lydian",
    "Mahj" -> "Mahajani",
    "Mand" -> "Mandaic, Mandaean",
    "Mani" -> "Manichaean",
    "Maya" -> "Mayan hieroglyphs",
    "Mend" -> "Mende",
    "Merc" -> "Meroitic Cursive",
    "Mero" -> "Meroitic Hieroglyphs",
    "Mlym" -> "Malayalam",
    "Mong" -> "Mongolian",
    "Moon" -> "Moon",
    "Mroo" -> "Mro, Mru",
    "Mtei" -> "Meitei Mayek (Meithei, Meetei)",
    "Mymr" -> "Myanmar (Burmese)",
    "Narb" -> "Old North Arabian",
    "Nbat" -> "Nabataean",
    "Nkgb" -> "Nakhi Geba",
    "Nkoo" -> "N’Ko",
    "Nshu" -> "Nüshu",
    "Ogam" -> "Ogham",
    "Olck" -> "Ol Chiki (Ol Cemet’, Ol, Santali)",
    "Orkh" -> "Old Turkic, Orkhon Runic",
    "Orya" -> "Oriya",
    "Osma" -> "Osmanya",
    "Palm" -> "Palmyrene",
    "Perm" -> "Old Permic",
    "Phag" -> "Phags-pa",
    "Phli" -> "Inscriptional Pahlavi",
    "Phlp" -> "Psalter Pahlavi",
    "Phlv" -> "Book Pahlavi",
    "Phnx" -> "Phoenician",
    "Plrd" -> "Miao (Pollard)",
    "Prti" -> "Inscriptional Parthian",
    "Qaaa" -> "Reserved for private use (start)",
    "Qabx" -> "Reserved for private use (end)",
    "Rjng" -> "Rejang (Redjang, Kaganga)",
    "Roro" -> "Rongorongo",
    "Runr" -> "Runic",
    "Samr" -> "Samaritan",
    "Sara" -> "Sarati",
    "Sarb" -> "Old South Arabian",
    "Saur" -> "Saurashtra",
    "Sgnw" -> "SignWriting",
    "Shaw" -> "Shavian (Shaw)",
    "Shrd" -> "Sharada, Śāradā",
    "Sind" -> "Khudawadi, Sindhi",
    "Sinh" -> "Sinhala",
    "Sora" -> "Sora Sompeng",
    "Sund" -> "Sundanese",
    "Sylo" -> "Syloti Nagri",
    "Syrc" -> "Syriac",
    "Syre" -> "Syriac (Estrangelo variant)",
    "Syrj" -> "Syriac (Western variant)",
    "Syrn" -> "Syriac (Eastern variant)",
    "Tagb" -> "Tagbanwa",
    "Takr" -> "Takri, Ṭākrī, Ṭāṅkrī",
    "Tale" -> "Tai Le",
    "Talu" -> "New Tai Lue",
    "Taml" -> "Tamil",
    "Tang" -> "Tangut",
    "Tavt" -> "Tai Viet",
    "Telu" -> "Telugu",
    "Teng" -> "Tengwar",
    "Tfng" -> "Tifinagh (Berber)",
    "Tglg" -> "Tagalog (Baybayin, Alibata)",
    "Thaa" -> "Thaana",
    "Thai" -> "Thai",
    "Tibt" -> "Tibetan",
    "Tirh" -> "Tirhuta",
    "Ugar" -> "Ugaritic",
    "Vaii" -> "Vai",
    "Visp" -> "Visible Speech",
    "Wara" -> "Warang Citi (Varang Kshiti)",
    "Wole" -> "Woleai",
    "Xpeo" -> "Old Persian",
    "Xsux" -> "Cuneiform, Sumero-Akkadian",
    "Yiii" -> "Yi",
    "Zinh" -> "Code for inherited script",
    "Zmth" -> "Mathematical notation",
    "Zsym" -> "Symbols",
    "Zxxx" -> "Code for unwritten documents",
    "Zyyy" -> "Code for undetermined script",
    "Zzzz" -> "Code for uncoded script"
  )
}