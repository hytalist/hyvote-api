package com.hytalist.hyvote.api

import java.util.UUID

/**
 * Public API for HyVote plugin.
 *
 * Allows other plugins to query vote data.
 *
 * Kotlin:
 * ```kotlin
 * val api = HyVoteAPI.get()
 * val totalVotes = api.getPlayerVotes(playerUuid)
 * val monthlyVotes = api.getPlayerVotesForMonth(playerUuid, 2025, 1)
 * ```
 *
 * Java:
 * ```java
 * HyVoteAPI api = HyVoteAPI.get();
 * int totalVotes = api.getPlayerVotes(playerUuid);
 * int monthlyVotes = api.getPlayerVotesForMonth(playerUuid, 2025, 1);
 * ```
 */
interface HyVoteAPI {

    companion object {
        @Volatile
        private var instance: HyVoteAPI? = null

        /**
         * Get the HyVoteAPI instance.
         * @return The API instance
         * @throws IllegalStateException if HyVote plugin is not loaded
         */
        @JvmStatic
        fun get(): HyVoteAPI {
            return instance ?: throw IllegalStateException("HyVote plugin is not loaded")
        }

        /**
         * Set the API instance (called by HyVote plugin on startup).
         */
        @JvmStatic
        fun setInstance(api: HyVoteAPI?) {
            instance = api
        }
    }

    // ========== Vote Counts ==========

    /**
     * Get total votes for a player (all-time).
     * @param playerUuid The player's UUID
     * @return Total vote count, or 0 if player never voted
     */
    fun getPlayerVotes(playerUuid: UUID): Int

    /**
     * Get votes for a player this month.
     * @param playerUuid The player's UUID
     * @return Vote count this month, or 0 if player hasn't voted this month
     */
    fun getPlayerVotesThisMonth(playerUuid: UUID): Int

    /**
     * Get votes for a player in the previous month.
     * @param playerUuid The player's UUID
     * @return Vote count in previous month, or 0 if player didn't vote
     */
    fun getPlayerVotesPreviousMonth(playerUuid: UUID): Int

    /**
     * Get votes for a player in a specific month.
     * @param playerUuid The player's UUID
     * @param year The year (e.g., 2025)
     * @param month The month (1-12)
     * @return Vote count for that month, or 0 if no data
     */
    fun getPlayerVotesForMonth(playerUuid: UUID, year: Int, month: Int): Int

    // ========== Rankings ==========

    /**
     * Get the player's rank all-time.
     * @param playerUuid The player's UUID
     * @return Pair of (rank, votes) or null if player hasn't voted
     */
    fun getPlayerRankAllTime(playerUuid: UUID): Pair<Int, Int>?

    /**
     * Get the player's rank this month.
     * @param playerUuid The player's UUID
     * @return Pair of (rank, votes) or null if player hasn't voted this month
     */
    fun getPlayerRankThisMonth(playerUuid: UUID): Pair<Int, Int>?

    /**
     * Get the player's rank in the previous month.
     * @param playerUuid The player's UUID
     * @return Pair of (rank, votes) or null if player didn't vote
     */
    fun getPlayerRankPreviousMonth(playerUuid: UUID): Pair<Int, Int>?

    /**
     * Get the player's rank for a specific month.
     * @param playerUuid The player's UUID
     * @param year The year (e.g., 2025)
     * @param month The month (1-12)
     * @return Pair of (rank, votes) or null if player didn't vote that month
     */
    fun getPlayerRankForMonth(playerUuid: UUID, year: Int, month: Int): Pair<Int, Int>?

    // ========== Top Voters ==========

    /**
     * Get top voters all-time.
     * @param limit Maximum number of voters to return (default 10)
     * @return List of VoterInfo objects sorted by votes descending
     */
    fun getTopVotersAllTime(limit: Int = 10): List<VoterInfo>

    /**
     * Get top voters this month.
     * @param limit Maximum number of voters to return (default 10)
     * @return List of VoterInfo objects sorted by votes descending
     */
    fun getTopVotersThisMonth(limit: Int = 10): List<VoterInfo>

    /**
     * Get top voters from the previous month.
     * @param limit Maximum number of voters to return (default 10)
     * @return List of VoterInfo objects sorted by votes descending
     */
    fun getTopVotersPreviousMonth(limit: Int = 10): List<VoterInfo>

    /**
     * Get top voters for a specific month.
     * @param year The year (e.g., 2025)
     * @param month The month (1-12)
     * @param limit Maximum number of voters to return (default 10)
     * @return List of VoterInfo objects sorted by votes descending
     */
    fun getTopVotersForMonth(year: Int, month: Int, limit: Int = 10): List<VoterInfo>

    // ========== Utility ==========

    /**
     * Get the last vote time for a player.
     * @param playerUuid The player's UUID
     * @return Epoch milliseconds of last vote, or null if never voted
     */
    fun getLastVoteTime(playerUuid: UUID): Long?

    /**
     * Voter information data class.
     */
    data class VoterInfo(
        /** The player's UUID */
        val uuid: UUID,
        /** The player's username (as of last vote) */
        val username: String,
        /** Number of votes */
        val votes: Int
    )
}
