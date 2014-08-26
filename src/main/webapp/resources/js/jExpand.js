(function($){
    $.fn.jExpand = function(){
        var element = this;

        $(element).find("tr:odd").addClass("odd");
        $(element).find("tr:not(.odd)").hide();
        $(element).find("tr:first-child").show();

        $(element).find("tr.odd").click(function() {
            $(this).next("tr").toggle();
        });
        
    }

    $.fn.show_all = function(){
        var element = this;
         $(element).find("tr.odd").next().show();
    }

    $.fn.hide_all = function(){
       var element = this;
       $(element).find("tr.odd").next().hide();
    }
})(jQuery);

(function($){
    $.fn.load_msa = function(id){
        var element = this;
        $.each($(element).find("tr.odd"),  function( key, value ) {

          var msa_div = "msa-" + value.id;
          var url = "get_mask?id="+id+"&marker="+value.id;
          //console.log( key + ": " + url );
          // $('#'+msa_div).css("display: block;") ;
           //$('#'+msa_div).load(url);


          /*biojs.io.fasta.parse.read(url, function(seqs){
            var msa = new biojs.vis.msa({el: document.getElementById(msa_div), seqs: seqs, zoomer: {labelWidth: 200}}).render();
          })*/
            biojs.io.fasta.parse.read(url, function(seqs){
                div_obj = document.getElementById(msa_div);
                seqs.map( function(item) {
                    var split = item.name.split("-");
                    if(split.length== 2){
                        item.name = split[1];
                    }
                });
                var msa = new biojs.vis.msa.msa({el: div_obj, seqs: seqs,zoomer: {labelWidth: 100}}) ;
                msa.g.vis.set("conserv",  false);
                //msa.g.colorscheme.set("nucleic");
                msa.g.colorscheme.set("scheme", "nucleic");
                msa.render();
                console.log(msa);
            });
        });



    }
})(jQuery);