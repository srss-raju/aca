/**
 * Enumeration of USPS compliant state codes in the United States
 */
package us.deloitteinnovation.aca.batch.util;


public enum StateCode {


    /**
     * Alabama
     *
     */
    AL,

    /**
     * Alaska
     *
     */
    AK,

    /**
     * American Samoa
     *
     */
    AS,

    /**
     * Arizona
     *
     */
    AZ,

    /**
     * Arkansas
     *
     */
    AR,

    /**
     * California
     *
     */
    CA,

    /**

     * Colorado
     *
     */
    CO,

    /**
     * Commonwealth of the Northern Mariana Islands
     *
     */
    MP,

    /**
     * Connecticut
     *
     */
    CT,

    /**
     * Delaware
     *
     */
    DE,

    /**
     * District of Columbia
     *
     */
    DC,

    /**
     * Federated States of Micronesia
     *
     */
    FM,

    /**
     * Florida
     *
     */
    FL,

    /**
     * Georgia
     *
     */
    GA,

    /**
     * Guam
     *
     */
    GU,

    /**
     * Hawaii
     *
     */
    HI,

    /**
     * Idaho
     *
     */
    ID,

    /**
     * Illinois
     *
     */
    IL,

    /**
     * Indiana
     *
     */
    IN,

    /**
     * Iowa
     *
     */
    IA,

    /**
     * Kansas
     *
     */
    KS,

    /**
     * Kentucky
     *
     */
    KY,

    /**
     * Louisiana
     *
     */
    LA,

    /**
     * Maine
     *
     */
    ME,

    /**
     * Marshall Islands
     *
     */
    MH,

    /**
     * Maryland
     *
     */
    MD,

    /**
     * Massachusetts
     *
     */
    MA,

    /**
     * Michigan
     *
     */
    MI,

    /**
     * Minnesota
     *
     */
    MN,

    /**
     * Mississippi
     *
     */
    MS,

    /**
     * Missouri
     *
     */
    MO,

    /**
     * Montana
     *
     */
    MT,

    /**
     * Nebraska
     *
     */
    NE,

    /**
     * Nevada
     *
     */
    NV,

    /**
     * New Hampshire
     *
     */
    NH,

    /**
     * New Jersey
     *
     */
    NJ,

    /**
     * New Mexico
     *
     */
    NM,

    /**
     * New York
     *
     */
    NY,

    /**
     * North Carolina
     *
     */
    NC,

    /**
     * North Dakota
     *
     */
    ND,

    /**
     * Ohio
     *
     */
    OH,

    /**
     * Oklahoma
     *
     */
    OK,

    /**
     * Oregon
     *
     */
    OR,

    /**
     * Palau
     *
     */
    PW,

    /**
     * Pennsylvania
     *
     */
    PA,

    /**
     * Puerto Rico
     *
     */
    PR,

    /**
     * Rhode Island
     *
     */
    RI,

    /**
     * South Carolina
     *
     */
    SC,

    /**
     * South Dakota
     *
     */
    SD,

    /**
     * Tennessee
     *
     */
    TN,

    /**
     * Texas
     *
     */
    TX,

    /**
     * U.S. Virgin Islands
     *
     */
    VI,

    /**
     * Utah
     *
     */
    UT,

    /**
     * Vermont
     *
     */
    VT,

    /**
     * Virginia
     *
     */
    VA,

    /**
     * Washington
     *
     */
    WA,

    /**
     * West Virginia
     *
     */
    WV,

    /**
     * Wisconsin
     *
     */
    WI,

    /**
     * Wyoming
     *
     */
    WY,

    /**
     * Armed Forces the Americas
     *
     */
    AA,

    /**
     * Armed Forces Europe
     *
     */
    AE,

    /**
     * Armed Forces Pacific
     *
     */
    AP;

    public String value() {
        return name();
    }

    public static StateCode fromValue(String v) {
        return valueOf(v);
    }
}
