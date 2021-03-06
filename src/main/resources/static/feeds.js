$(function () {
    /**
     * Returns the difference, in minutes, between now and the date stored in #lastModifiedDate
     * @returns {number}
     */
    function getElapsedTime() {
        var lastModDate = new Date($("#lastModifiedDate").text());
        var now = new Date();
        var minutes = Math.round((now - lastModDate) / 1000 / 60);
        return minutes;
    }

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
                    if (feeds[feedUrl] === true) {
                        $(this).addClass("stale");
                        $(this).parent(".grid-item").attr("data-read", "1");
                    }
                });
            }
            // save the feeds to local storage
            feeds = parseFeeds();
            saveFeeds(feeds);
        };
    }

    var feeds = new Feeds($);
    feeds.shadeFeedLinks();

    $(".grid").isotope({
        getSortData: {
            isUnread: function(itemElem) {
                var unreadLinkCount = $( itemElem ).find('.feedUrl:not(.stale)').length;
                console.log(itemElem.id + " : " + (unreadLinkCount>0));
                return (unreadLinkCount > 0);
            },
            epochTime: '[data-epoch] parseInt'
        },
        layoutMode: 'packery',
        itemSelector: '.grid-item',
        sortBy: ['isUnread', 'epochTime'],
        sortAscending: false
    });

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
    setInterval(function () {
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
