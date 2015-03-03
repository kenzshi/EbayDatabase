
/**
 * Provides suggestions for state names (USA).
 * @class
 * @scope public
 */
function StateSuggestions() {
    this.states = [];
}

/**
 * Request suggestions for the given autosuggest control. 
 * @scope protected
 * @param oAutoSuggestControl The autosuggest control to provide suggestions for.
 */
StateSuggestions.prototype.requestSuggestions = function (oAutoSuggestControl /*:AutoSuggestControl*/,
                                                          bTypeAhead /*:boolean*/) {
    var aSuggestions = [];
    var sTextboxValue = oAutoSuggestControl.textbox.value;
    
    if (sTextboxValue.length > 0) {
        var xmlHttpReq = new XMLHttpRequest();
        var url = "suggest?q="+sTextboxValue;
        xmlHttpReq.open("GET", url);
        xmlHttpReq.onreadystatechange = function(xmlHttpReq) {
            if (this.readyState==4) {
                var aSuggestions = [];
                var response = this.responseXML.getElementsByTagName('CompleteSuggestion');
                var suggestion="";
                for (i=0; i<response.length; i++) {
                    suggestion=response[i].childNodes[0].getAttribute('data');
                    aSuggestions.push(suggestion);
                }
                //provide suggestions to the control
                oAutoSuggestControl.autosuggest(aSuggestions, bTypeAhead);
            }  
            
        }
        xmlHttpReq.send(null);
    }
};