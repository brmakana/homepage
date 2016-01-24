$(function () {
    var elem = document.querySelector('.grid');
    var msnry = new Masonry( elem, {
        // options
        itemSelector: '.grid-item',
        columnWidth: 200
    });

    // element argument can be a selector string
    //   for an individual element
    var msnry = new Masonry( '.grid', {
        // options
    });

    /**
     * Returns the difference, in minutes, between now and the date stored in #lastModifiedDate
     * @returns {number}
     */
    function getElapsedTime() {
        var lastModDate = new Date($("#lastModifiedDate").text());
        var now = new Date();
        var minutes = Math.round((now - lastModDate) / 1000 / 60);
        return minutes;
    };

    function Feeds($) {

        function parseFeeds() {
            var feeds = {};
            $(".feedUrl").each(function () {
                feedUrl = $(this).attr("href");
                feeds[feedUrl] = true;
            });
            return feeds;
        }

        function saveFeeds(feeds) {
            if (typeof(Storage) !== "undefined") {
                var feedString = JSON.stringify(feeds);
                localStorage["feeds"] = feedString;
            }
            else {
                console.log("HTML5 Storage not supported!");
            }
        }

        function loadFeeds() {
            if (typeof(Storage) !== "undefined") {
                if (localStorage["feeds"] !== undefined && localStorage["feeds"] !== null) {
                    var feedString = localStorage["feeds"];
                    return JSON.parse(feedString);
                }
                else {
                    return null;
                }
            }
            else {
                console.log("HTML5 Storage not supported!");
            }
        }

        this.shadeFeedLinks = function () {
            // first try and load already seen feeds from local storage
            var feeds = loadFeeds();
            if (feeds) {
                // next, check each feed to see if it's been seen before, and if so colorize it
                $(".feedUrl").each(function () {
                    feedUrl = $(this).attr("href");
                    if (feeds[feedUrl] == true) {
                        $(this).addClass("stale");
                    }
                });
            }
            // save the feeds to local storage
            feeds = parseFeeds();
            saveFeeds(feeds);
        }
    }

    var feeds = new Feeds($);
    feeds.shadeFeedLinks();

    /**
     * Reload the page when the remaining time element is clicked
     */
    $(".remainingTime").click(function () {
        location.reload();
    });

    /** Sets a timer that executes every 3 seconds, updating the remainingTime element
     *
     * @type {number}
     */
    var timerId = setInterval(function () {
        var elapsedTime = getElapsedTime();
        $(".remainingTime").html(elapsedTime + " minutes ago");
        if (elapsedTime > 45) {
            $(".remainingTime").addClass("alert");
        }
        else {
            $(".remainingTime").removeClass("alert");
        }
    }, 3000);

});