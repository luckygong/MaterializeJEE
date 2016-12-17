(function ($, moment)
{
   var pluginName = "materialDatePicker";
   var pluginDataName = "plugin_" + pluginName;
   
   var month_en = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
   var month_zh = [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ];
   var month_map = {};
   month_map["zh-cn"] = month_zh;
   month_map["en"] = month_en;
   
   var week_en = [ "Sun", "Mon", "Tue", "Wen", "Thu", "Fri", "Sat" ];
   var week_zh = [ "日", "一", "二", "三", "四", "五", "六"];
   var week_map = {};
   week_map["zh-cn"] = week_zh;
   week_map["en"] = week_en;

   function Plugin(element, options)
   {
      this.currentView = 0;

      this.minDate;
      this.maxDate;

      this._attachedEvents = [];

      this.element = element;
      this.$element = $(element);

       this.params = {
		   date: true, 
		   time: true, 
		   format: null, 
		   minDate: null, 
		   maxDate: null, 
		   currentDate: null, 
		   shortTime: true,
		   lang: 'zh-cn', 
		   okText: '确定', 
		   clearText: '清空', 
		   cancelText: '取消', 
		   nowText: '今日', 
		   switchOnClick: false, 
		   triggerEvent: 'focus'
		};
      this.params = $.fn.extend(this.params, options);
      this.params.weekStart = 0;
	  var format = this.params.format;
	  var date = this.params.date;
	  var time = this.params.time;
	  if(format == null || format == '' || typeof(format) == undefined){
		  if(date && !time){
			  format = 'YYYY-MM-DD';
		  }
		  if(date && time) {
			  format = 'YYYY-MM-DD HH:mm';
		  }
		  if(!date && time){
			  format = 'HH:mm';
		  }
		  this.params.format = format;
	  }
	  

      this.name = "dtp_" + this.setName();
      this.$element.attr("data-dtp", this.name);

      moment.locale(this.params.lang);

      this.init();
   }

   $.fn[pluginName] = function (options, p)
   {
      this.each(function ()
      {
         if (!$.data(this, pluginDataName))
         {
            $.data(this, pluginDataName, new Plugin(this, options));
         } else
         {
            if (typeof ($.data(this, pluginDataName)[options]) === 'function')
            {
               $.data(this, pluginDataName)[options](p);
            }
            if (options === 'destroy')
            {
               delete $.data(this, pluginDataName);
            }
         }
      });
      return this;
   };

   Plugin.prototype =
           {
              init: function ()
              {
                 this.initDays();
                 this.initDates();

                 this.initTemplate();
				 this.initYears();

                 this.initButtons();

                 this._attachEvent(this.$dtpElement.find('.picker__holder'), 'click', this._onElementClick.bind(this));
                 this._attachEvent(this.$dtpElement.find('.picker__close > a'), 'click', this._onCloseClick.bind(this));
                 this._attachEvent(this.$dtpElement.find('.picker__select--year'), 'change', this._onYearChange.bind(this));
                 this._attachEvent(this.$dtpElement.find('.picker__select--month'), 'change', this._onMonthChange.bind(this));
                 this._attachEvent(this.$element, this.params.triggerEvent, this._fireCalendar.bind(this));
              },
			  initYears: function(nowYear)
			  {
				  if(!nowYear){
					  nowYear = moment().format("YYYY");
				  }
				  var nowYear = parseInt(this.currentDate.locale(this.params.lang).format("YYYY"));
				  var beginYear = nowYear - 7;
				  var endYear = nowYear + 7;
				  
				  var yearOptions = '';
				  for(var i = beginYear;i <= endYear; i++){
					  if(i==nowYear){
						  yearOptions += '<option value="'+i+'" selected>'+i+'</option>';
					  }else{
						  yearOptions += '<option value="'+i+'">'+i+'</option>';
					  }
				  }
				  $(".picker__select--year").html(yearOptions);
			  },
              initDays: function ()
              {
                 this.days = [];
                 for (var i = this.params.weekStart; this.days.length < 7; i++)
                 {
                    if (i > 6)
                    {
                       i = 0;
                    }
                    this.days.push(i.toString());
                 }
              },
              initDates: function ()
              {
                 if (this.$element.val().length > 0)
                 {
                    if (typeof (this.params.format) !== 'undefined' && this.params.format !== null)
                    {
                       this.currentDate = moment(this.$element.val(), this.params.format).locale(this.params.lang);
                    } else
                    {
                       this.currentDate = moment(this.$element.val()).locale(this.params.lang);
                    }
                 } else
                 {
                    if (typeof (this.$element.attr('value')) !== 'undefined' && this.$element.attr('value') !== null && this.$element.attr('value') !== "")
                    {
                       if (typeof (this.$element.attr('value')) === 'string')
                       {
                          if (typeof (this.params.format) !== 'undefined' && this.params.format !== null)
                          {
                             this.currentDate = moment(this.$element.attr('value'), this.params.format).locale(this.params.lang);
                          } else
                          {
                             this.currentDate = moment(this.$element.attr('value')).locale(this.params.lang);
                          }
                       }
                    } else
                    {
                       if (typeof (this.params.currentDate) !== 'undefined' && this.params.currentDate !== null)
                       {
                          if (typeof (this.params.currentDate) === 'string')
                          {
                             if (typeof (this.params.format) !== 'undefined' && this.params.format !== null)
                             {
                                this.currentDate = moment(this.params.currentDate, this.params.format).locale(this.params.lang);
                             } else
                             {
                                this.currentDate = moment(this.params.currentDate).locale(this.params.lang);
                             }
                          } else
                          {
                             if (typeof (this.params.currentDate.isValid) === 'undefined' || typeof (this.params.currentDate.isValid) !== 'function')
                             {
                                var x = this.params.currentDate.getTime();
                                this.currentDate = moment(x, "x").locale(this.params.lang);
                             } else
                             {
                                this.currentDate = this.params.currentDate;
                             }
                          }
                          this.$element.val(this.currentDate.format(this.params.format));
                       } else
                          this.currentDate = moment();
                    }
                 }

                 if (typeof (this.params.minDate) !== 'undefined' && this.params.minDate !== null)
                 {
                    if (typeof (this.params.minDate) === 'string')
                    {
                       if (typeof (this.params.format) !== 'undefined' && this.params.format !== null)
                       {
                          this.minDate = moment(this.params.minDate, this.params.format).locale(this.params.lang);
                       } else
                       {
                          this.minDate = moment(this.params.minDate).locale(this.params.lang);
                       }
                    } else
                    {
                       if (typeof (this.params.minDate.isValid) === 'undefined' || typeof (this.params.minDate.isValid) !== 'function')
                       {
                          var x = this.params.minDate.getTime();
                          this.minDate = moment(x, "x").locale(this.params.lang);
                       } else
                       {
                          this.minDate = this.params.minDate;
                       }
                    }
                 } else if (this.params.minDate === null)
                 {
                    this.minDate = null;
                 }

                 if (typeof (this.params.maxDate) !== 'undefined' && this.params.maxDate !== null)
                 {
                    if (typeof (this.params.maxDate) === 'string')
                    {
                       if (typeof (this.params.format) !== 'undefined' && this.params.format !== null)
                       {
                          this.maxDate = moment(this.params.maxDate, this.params.format).locale(this.params.lang);
                       } else
                       {
                          this.maxDate = moment(this.params.maxDate).locale(this.params.lang);
                       }
                    } else
                    {
                       if (typeof (this.params.maxDate.isValid) === 'undefined' || typeof (this.params.maxDate.isValid) !== 'function')
                       {
                          var x = this.params.maxDate.getTime();
                          this.maxDate = moment(x, "x").locale(this.params.lang);
                       } else
                       {
                          this.maxDate = this.params.maxDate;
                       }
                    }
                 } else if (this.params.maxDate === null)
                 {
                    this.maxDate = null;
                 }

                 if (!this.isAfterMinDate(this.currentDate))
                 {
                    this.currentDate = moment(this.minDate);
                 }
                 if (!this.isBeforeMaxDate(this.currentDate))
                 {
                    this.currentDate = moment(this.maxDate);
                 }
              },
              initTemplate: function ()
              {
				  
				 this.template = '<div class="picker" id="' + this.name + '" tabindex="0">' +
					'<div class="picker__holder">' +
						'<div class="picker__frame">' +
							'<div class="picker__wrap">' +
								'<div class="picker__box" style="max-width: 300px;">' +
									'<div class="picker__date-display">' +
										'<div class="row" style="margin-bottom:0px"><div class="picker__close right" style="padding-right:0px;"><a href="javascript:void(0);"><i class="mdi-content-clear" style="color:white"></i></a></div></div>' +
										'<div class="picker__month-display"></div>' +
										'<div class="picker__day-display"></div>' +
										'<div class="picker__year-display"></div>' +
										'<div class="picker__time-display"></div>' +
									'</div>' +
									'<div class="picker__calendar-container">' +
										'<div id="calendar-div">' +
											'<div class="picker__header">' +
												'<select class="picker__select--month browser-default" aria-controls="' + this.name + '_table" title="Select a month">';
												var month_full = month_map[this.params.lang]
												for(var i=0; i<month_full.length;i++){
													this.template += '<option value="'+i+'">'+month_full[i]+'</option>';
												}
												this.template += '</select>' +
												'<select class="picker__select--year browser-default" aria-controls="' + this.name + '_table" title="Select a year">' +
												'</select>' +
												'<div class="picker__nav--prev" data-nav="-1" role="button" aria-controls="' + this.name + '_table" title="Previous month"></div>' +
												'<div class="picker__nav--next" data-nav="1" role="button" aria-controls="' + this.name + '_table" title="Next month"></div>' +
											'</div>' +
											'<table class="picker__table" id="' + this.name + '_table" role="grid" aria-controls="' + this.name + '" aria-readonly="true">' +
												'<thead>' +
													'<tr>' ;
													var week_full = week_map[this.params.lang]
													for(var i=0; i<week_full.length;i++){
														this.template += '<th class="picker__weekday" scope="col">'+week_full[i]+'</th>';
													}
													this.template += '</tr>' +
												'</thead>' +
												'<tbody>' +
												'</tbody>' +
											'</table>' +
											'<div class="picker__footer" style="text-align: center;">' +
												'<a class="btn-flat waves-effect cyan-text text-darken-2 picker__today" style="width:50px;padding-left:0px;padding-right:0px;"><b>'+this.params.nowText+'</b></a>' +
												'<a class="btn-flat waves-effect red-text text-darken-4 picker__clear" style="width:50px;padding-left:0px;padding-right:0px"><b>'+this.params.clearText+'</b></a>' +
												'<a class="btn-flat waves-effect cyan-text text-darken-2 picker__ok" style="width:50px;padding-left:0px;padding-right:0px"><b>'+this.params.okText+'</b></a>' +
											'</div>' +
										'</div>' +
										'<div id="time-div">' +
											'<div class="picker__table">' +
												'<div class="time-show" style="margin-top: 10px;font-size: larger;"></div>' +
												'<div class="picker_time__circularHolder">' +
													'<div class="btn-flat waves-effect picker_time__am" role="button">AM</div>'+
													'<div class="btn-flat waves-effect picker_time__pm" role="button">PM</div>'+
													'<div id="picker_time__needle" class="">' +
														'<span class="picker_time__dot"></span>' +
														'<span class="picker_time__line"></span>' +
														'<span id="picker-time__circle" class="picker_time__circle"></span>' +
													'</div>' +
													'<div id="mddtp-time__minuteView" class="picker_time__circularView picker_time__circularView--hidden">' +
														'<div class="picker_time__cell picker_time__cell--rotate-10"><span>05</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-20"><span>10</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-30"><span>15</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-40"><span>20</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-50"><span>25</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-60"><span>30</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-70"><span>35</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-80"><span>40</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-90"><span>45</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-100"><span>50</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-110"><span>55</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-120"><span>00</span></div>' +
													'</div>' +
													'<div id="mddtp-time__hourView" class="picker_time__circularView">' +
														'<div class="picker_time__cell picker_time__cell--rotate-5"><span>1</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-10"><span>2</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-15"><span>3</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-20"><span>4</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-25"><span>5</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-30"><span>6</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-35"><span>7</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-40"><span>8</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-45"><span>9</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-50"><span>10</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-55"><span>11</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-60"><span>12</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-65"><span>13</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-70"><span>14</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-75"><span>15</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-80"><span>16</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-85"><span>17</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-90"><span>18</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-95"><span>19</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-100"><span>20</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-105"><span>21</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-110"><span>22</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-115"><span>23</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-120"><span>00</span></div>' +
													'</div>' +
													'<div id="mddtp-time__short_hourView" class="picker_time__circularView">' +
														'<div class="picker_time__cell picker_time__cell--rotate-10"><span>1</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-20"><span>2</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-30"><span>3</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-40"><span>4</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-50"><span>5</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-60"><span>6</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-70"><span>7</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-80"><span>8</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-90"><span>9</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-100"><span>10</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-110"><span>11</span></div>' +
														'<div class="picker_time__cell picker_time__cell--rotate-120"><span>12</span></div>' +
													'</div>' +
												'</div>' +
											'</div>' +
											'<div class="picker__footer" style="text-align: center;">' +
												'<a class="btn-flat waves-effect red-text text-darken-4 picker__clear" style="width:50px;padding-left:0px;padding-right:0px"><b>'+this.params.clearText+'</b></a>' +
												'<a class="btn-flat waves-effect red-text text-darken-4 picker__cancel" style="width:50px;padding-left:0px;padding-right:0px"><b>'+this.params.cancelText+'</b></a>' +
												'<a class="btn-flat waves-effect cyan-text text-darken-2 picker__ok" style="width:50px;padding-left:0px;padding-right:0px"><b>'+this.params.okText+'</b></a>' +
											'</div>' +
										'</div>' +
									'</div>' +
								'</div>' +
							'</div>' +
						'</div>' +
					'</div>' +
				'</div> ';

                 if ($('body').find("#" + this.name).length <= 0)
                 {
                    $('body').append(this.template);

                    if (this)
                       this.dtpElement = $('body').find("#" + this.name);
                    this.$dtpElement = $(this.dtpElement);
                 }
              },
              initButtons: function ()
              {
                 this._attachEvent(this.$dtpElement.find('.picker__today'), 'click', this._onNowClick.bind(this));
                 this._attachEvent(this.$dtpElement.find('.picker__clear'), 'click', this._onClearClick.bind(this));
                 this._attachEvent(this.$dtpElement.find('.picker__ok'), 'click', this._onOKClick.bind(this));
                 this._attachEvent(this.$dtpElement.find('.picker__cancel'), 'click', this._onCancelClick.bind(this));
                 this._attachEvent(this.$dtpElement.find('.picker__nav--prev'), 'click', this._onMonthBeforeClick.bind(this));
                 this._attachEvent(this.$dtpElement.find('.picker__nav--next'), 'click', this._onMonthAfterClick.bind(this));
				 this._attachEvent(this.$dtpElement.find('.picker_time__am'), 'click', this._onSelectAM.bind(this));
                 this._attachEvent(this.$dtpElement.find('.picker_time__pm'), 'click', this._onSelectPM.bind(this));
                 this._attachEvent(this.$dtpElement.find('.picker_time__cell'), 'click', this._onTimeCellClick.bind(this));
              },
              initDate: function (d)
              {
                 this.currentView = 0;

                 this.$dtpElement.find('#calendar-div').removeClass('hide');
				 
                 this.$dtpElement.find('.picker__time-display').addClass('hide');
                 this.$dtpElement.find('#time-div').addClass('hide');

                 var _date = ((typeof (this.currentDate) !== 'undefined' && this.currentDate !== null) ? this.currentDate : null);
                 var _calendar = this.generateCalendar(this.currentDate);

                 if (typeof (_calendar.week) !== 'undefined' && typeof (_calendar.days) !== 'undefined')
                 {
                    var _template = this.constructHTMLCalendar(_date, _calendar);

                    this.$dtpElement.find('.picker__day--infocus').off('click');
                    this.$dtpElement.find('#calendar-div .picker__table tbody').html(_template);

                    this.$dtpElement.find('.picker__day--infocus').on('click', this._onSelectDate.bind(this));
                 }

                 this.showDate(_date);
              },
              _onSelectAM: function (e)
              {
                 $(e.currentTarget).addClass('picker_time__ampm_active');
				 this.$dtpElement.find('.picker_time__pm').removeClass('picker_time__ampm_active');
                 
                 if (this.currentDate.hour() >= 12)
                 {
                    if (this.currentDate.subtract(12, 'hours'))
                       this.showTime(this.currentDate);
                 }
                 this.toggleTime((this.currentView === 1));
              },
              _onSelectPM: function (e)
              {
                 $(e.currentTarget).addClass('picker_time__ampm_active');
				 this.$dtpElement.find('.picker_time__am').removeClass('picker_time__ampm_active');

                 if (this.currentDate.hour() < 12)
                 {
                    if (this.currentDate.add(12, 'hours'))
                       this.showTime(this.currentDate);
                 }
                 this.toggleTime((this.currentView === 1));
              },
			  convertHours: function (h)
              {
                 var _return = h;

                 if (this.params.shortTime === true)
                 {
                    if ((h < 12) && this.isPM())
                    {
                       _return += 12;
                    }
                 }

                 return _return;
              },
			  isPM: function ()
              {
                 return this.$dtpElement.find('.picker_time__pm').hasClass('picker_time__ampm_active');
              },
			  initHours: function ()
              {
                 this.currentView = 1;

                 this.showTime(this.currentDate);

				 this.$dtpElement.find('#calendar-div').addClass('hide');
				 this.$dtpElement.find('#time-div').removeClass('hide');
				 this.$dtpElement.find('#mddtp-time__minuteView').addClass('picker_time__circularView--hidden');
				 if (this.params.shortTime){
					 this.$dtpElement.find('.picker_time__am').removeClass('hide');
					 this.$dtpElement.find('.picker_time__pm').removeClass('hide');
					 this.$dtpElement.find('#mddtp-time__short_hourView').removeClass('picker_time__circularView--hidden');
					 this.$dtpElement.find('#mddtp-time__hourView').addClass('picker_time__circularView--hidden');
					 if (this.currentDate.hour() < 12){
						this.$dtpElement.find('.picker_time__am').click();
					 } else {
						this.$dtpElement.find('.picker_time__pm').click();
					 }
				 }else{
					 this.$dtpElement.find('.picker_time__am').addClass('hide');
					 this.$dtpElement.find('.picker_time__pm').addClass('hide');
					 this.$dtpElement.find('#mddtp-time__short_hourView').addClass('picker_time__circularView--hidden');
					 this.$dtpElement.find('#mddtp-time__hourView').removeClass('picker_time__circularView--hidden');
				 }
				 
				 if(!this.params.date){
					 this.$dtpElement.find('.picker__time-display').removeClass('hide');
					 this.$dtpElement.find('.time-show').addClass('hide');
					 this.$dtpElement.find('.picker__year-display').addClass('hide');
					 this.$dtpElement.find('.picker__month-display').addClass('hide');
					 this.$dtpElement.find('.picker__day-display').addClass('hide');
					 this.$dtpElement.find('.picker__cancel').addClass('hide');
				 }
				 
                 this.moveNeedle();
              },
              initMinutes: function ()
              {
                 this.currentView = 2;

                 this.showTime(this.currentDate);

                 this.$dtpElement.find('#calendar-div').addClass('hide');
				 this.$dtpElement.find('#time-div').removeClass('hide');
				 this.$dtpElement.find('#mddtp-time__hourView').addClass('picker_time__circularView--hidden');
				 this.$dtpElement.find('#mddtp-time__short_hourView').addClass('picker_time__circularView--hidden');
				 this.$dtpElement.find('#mddtp-time__minuteView').removeClass('picker_time__circularView--hidden');
				 this.$dtpElement.find('.picker__cancel').removeClass('hide');
				 
                 if(!this.params.date){
					 this.$dtpElement.find('.picker__time-display').removeClass('hide');
					 this.$dtpElement.find('.time-show').addClass('hide');
					 this.$dtpElement.find('.picker__year-display').addClass('hide');
					 this.$dtpElement.find('.picker__month-display').addClass('hide');
					 this.$dtpElement.find('.picker__day-display').addClass('hide');
				 }
                 this.moveNeedle();
              },
              moveNeedle: function ()
              {
                 var H = this.currentDate.hour();
                 var M = this.currentDate.minute();
				 
				 if(this.currentView == 1){
					 if(H == 0){
						 H = 24;
					 }
					 if (this.params.shortTime){
						 if (H > 12){
							H = H - 12;
						 }
						 var classes = "picker_time__cell--rotate-" + H*10;
						 this.$dtpElement.find("#picker_time__needle").attr("class","");
						 this.$dtpElement.find("#picker_time__needle").addClass("picker_time__selection");
						 this.$dtpElement.find("#picker_time__needle").addClass(classes);
					 }else{
						 var classes = "picker_time__cell--rotate-" + H*5;
						 this.$dtpElement.find("#picker_time__needle").attr("class","");
						 this.$dtpElement.find("#picker_time__needle").addClass("picker_time__selection");
						 this.$dtpElement.find("#picker_time__needle").addClass(classes);
					 }
				 }
				 if(this.currentView == 2){
					 if(M == 0){
						 M = 60;
					 }
					 var classes = "picker_time__cell--rotate-" + M*2;
					 this.$dtpElement.find("#picker_time__needle").attr("class","");
					 this.$dtpElement.find("#picker_time__needle").addClass("picker_time__selection");
					 this.$dtpElement.find("#picker_time__needle").addClass(classes);
				 }
				 $("#picker_time__needle").toggle();
              },
              isAfterMinDate: function (date, checkHour, checkMinute)
              {
                 var _return = true;

                 if (typeof (this.minDate) !== 'undefined' && this.minDate !== null)
                 {
                    var _minDate = moment(this.minDate);
                    var _date = moment(date);

                    if (!checkHour && !checkMinute)
                    {
                       _minDate.hour(0);
                       _minDate.minute(0);

                       _date.hour(0);
                       _date.minute(0);
                    }

                    _minDate.second(0);
                    _date.second(0);
                    _minDate.millisecond(0);
                    _date.millisecond(0);

                    if (!checkMinute)
                    {
                       _date.minute(0);
                       _minDate.minute(0);

                       _return = (parseInt(_date.format("X")) >= parseInt(_minDate.format("X")));
                    } else
                    {
                       _return = (parseInt(_date.format("X")) >= parseInt(_minDate.format("X")));
                    }
                 }

                 return _return;
              },
              isBeforeMaxDate: function (date, checkTime, checkMinute)
              {
                 var _return = true;

                 if (typeof (this.maxDate) !== 'undefined' && this.maxDate !== null)
                 {
                    var _maxDate = moment(this.maxDate);
                    var _date = moment(date);

                    if (!checkTime && !checkMinute)
                    {
                       _maxDate.hour(0);
                       _maxDate.minute(0);

                       _date.hour(0);
                       _date.minute(0);
                    }

                    _maxDate.second(0);
                    _date.second(0);
                    _maxDate.millisecond(0);
                    _date.millisecond(0);

                    if (!checkMinute)
                    {
                       _date.minute(0);
                       _maxDate.minute(0);

                       _return = (parseInt(_date.format("X")) <= parseInt(_maxDate.format("X")));
                    } else
                    {
                       _return = (parseInt(_date.format("X")) <= parseInt(_maxDate.format("X")));
                    }
                 }

                 return _return;
              },
              showDate: function (date)
              {
                 if (date)
                 {
                    this.$dtpElement.find('.picker__month-display').html(date.locale(this.params.lang).format('MMMM'));
                    this.$dtpElement.find('.picker__day-display').html(date.locale(this.params.lang).format('D'));
                    this.$dtpElement.find('.picker__year-display').html(date.locale(this.params.lang).format('YYYY'));
                 }
              },
              showTime: function (date)
              {
                 if (date)
                 {
                    var minutes = date.minute();
                    var content = ((this.params.shortTime) ? date.format('hh') : date.format('HH')) + ':' + ((minutes.toString().length == 2) ? minutes : '0' + minutes) + ((this.params.shortTime) ? ' ' + date.format('A') : '');

                    this.$dtpElement.find('.picker__time-display').html(content);
					this.$dtpElement.find('.time-show').html(content);
                 }
              },
              selectDate: function (date)
              {
                 if (date)
                 {
                    this.currentDate.year(parseInt(moment(date).format("YYYY")));
                    this.currentDate.month(parseInt(moment(date).format("MM"))-1);
                    this.currentDate.date(parseInt(moment(date).format("DD")));

                    this.showDate(this.currentDate);
                    this.$element.trigger('dateSelected', this.currentDate);
                 }
              },
              generateCalendar: function (date)
              {
                 var _calendar = {};

                 if (date !== null)
                 {
                    var startOfMonth = moment(date).locale(this.params.lang).startOf('month');
                    var endOfMonth = moment(date).locale(this.params.lang).endOf('month');

                    var iNumDay = startOfMonth.format('d');

                    _calendar.week = this.days;
                    _calendar.days = [];

                    for (var i = startOfMonth.date(); i <= endOfMonth.date(); i++)
                    {
                       if (i === startOfMonth.date())
                       {
                          var iWeek = _calendar.week.indexOf(iNumDay.toString());
                          if (iWeek > 0)
                          {
                             for (var x = 0; x < iWeek; x++)
                             {
                                _calendar.days.push(0);
                             }
                          }
                       }
                       _calendar.days.push(moment(startOfMonth).locale(this.params.lang).date(i));
                    }
                 }

                 return _calendar;
              },
              constructHTMLCalendar: function (date, calendar)
              {
                 var _template = "";
				 
				 var year = date.locale(this.params.lang).format("YYYY");
				 var month = parseInt(date.locale(this.params.lang).format("M"))-1;
				 this.$dtpElement.find('.picker__select--month').val(month);
				 this.$dtpElement.find('.picker__select--year').val(year);

                 _template += '<tr>';

                 for (var i = 0; i < calendar.days.length; i++)
                 {
                    if (i % 7 == 0)
                       _template += '</tr><tr>';
                    _template += '<td role="presentation">';
                    if (calendar.days[i] != 0)
                    {
                       if (this.isBeforeMaxDate(moment(calendar.days[i]), false, false) === false || this.isAfterMinDate(moment(calendar.days[i]), false, false) === false)
                       {
                          _template += '<div class="picker__day" style="color:rgba(89, 89, 89, 0.3);letter-spacing:-.3;padding:.75rem 0;font-weight:400;border: 1px solid transparent;" disabled="disabled">' + moment(calendar.days[i]).locale(this.params.lang).format("DD") + '</div>';
                       } else
                       {
                          if (moment(calendar.days[i]).locale(this.params.lang).format("DD") === moment(this.currentDate).locale(this.params.lang).format("DD"))
                          {
                             _template += '<div class="picker__day picker__day--infocus picker__day--selected picker__day--highlighted" data-date="'+moment(calendar.days[i]).format("YYYY-MM-DD")+'">' + moment(calendar.days[i]).locale(this.params.lang).format("DD") + '</div>';
                          } else
                          {
                             _template += '<div class="picker__day picker__day--infocus" data-date="'+moment(calendar.days[i]).format("YYYY-MM-DD")+'">' + moment(calendar.days[i]).locale(this.params.lang).format("DD") + '</div>';
                          }
                       }

                       _template += '</td>';
                    }
                 }
                 _template += '</tr>';

                 return _template;
              },
              setName: function ()
              {
                 var text = "";
                 var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

                 for (var i = 0; i < 5; i++)
                 {
                    text += possible.charAt(Math.floor(Math.random() * possible.length));
                 }

                 return text;
              },
              setElementValue: function ()
              {
                 this.$element.trigger('beforeChange', this.currentDate);
                 if (typeof ($.material) !== 'undefined')
                 {
                    this.$element.removeClass('empty');
                 }
                 this.$element.val(moment(this.currentDate).locale(this.params.lang).format(this.params.format));
                 this.$element.trigger('change', this.currentDate);
              },
              toggleTime: function (value, isHours)
              {
                 var result = false;

                 if (isHours)
                 {
                    var _date = moment(this.currentDate);
                    _date.hour(value).minute(0).second(0);

                    result = !(this.isAfterMinDate(_date, true, false) === false || this.isBeforeMaxDate(_date, true, false) === false);
                 } else
                 {
                    var _date = moment(this.currentDate);
                    _date.minute(value).second(0);

                    result = !(this.isAfterMinDate(_date, true, true) === false || this.isBeforeMaxDate(_date, true, true) === false);
                 }

                 return result;
              },
              _attachEvent: function (el, ev, fn)
              {
                 el.on(ev, null, null, fn);
                 this._attachedEvents.push([el, ev, fn]);
              },
              _detachEvents: function ()
              {
                 for (var i = this._attachedEvents.length - 1; i >= 0; i--)
                 {
                    this._attachedEvents[i][0].off(this._attachedEvents[i][1], this._attachedEvents[i][2]);
                    this._attachedEvents.splice(i, 1);
                 }
              },
              _fireCalendar: function ()
              {
                 this.currentView = 0;
                 this.$element.blur();

                 this.initDates();

                 this.$dtpElement.addClass("picker--opened");
                 this.$dtpElement.addClass("picker--focused");

                 if (this.params.date)
                 {
                    this.initDate();
                 } else
                 {
                    if (this.params.time)
                    {
                       this.initHours();
                    }
                 }
              },
              _onTimeCellClick: function (e)
              {
                 var value = parseInt($(e.currentTarget).find("span").text());
				 if(this.currentView == 1){
					 this.currentDate.hour(value);
					 if (this.params.shortTime && this.isPM()){
						 this.currentDate.add(12, 'hours');
					 }
				 }
				 if(this.currentView == 2){
					 this.currentDate.minute(value);
				 }
				 this.showTime(this.currentDate);
				 this.moveNeedle();
              }
			  ,_onBackgroundClick: function (e)
              {
                 e.stopPropagation();
                 this._onCloseClick();
              },
              _onElementClick: function (e)
              {
                 e.stopPropagation();
              },
              _onCloseClick: function ()
              {
                 this.$dtpElement.removeClass("picker--opened");
                 this.$dtpElement.removeClass("picker--focused");
              },
              _onClearClick: function ()
              {
                 this.currentDate = null;
                 this.$element.trigger('beforeChange', this.currentDate);
                 this._onCloseClick();
                 if (typeof ($.material) !== 'undefined')
                 {
                    this.$element.addClass('empty');
                 }
                 this.$element.val('');
                 this.$element.trigger('change', this.currentDate);
              },
              _onNowClick: function ()
              {
                 this.currentDate = moment();

                 if (this.params.date === true)
                 {
                    this.showDate(this.currentDate);

                    if (this.currentView === 0)
                    {
                       this.initDate();
                    }
                 }

                 if (this.params.time === true)
                 {
                    this.showTime(this.currentDate);

                    switch (this.currentView)
                    {
                       case 1 :
                          this.initHours();
                          break;
                       case 2 :
                          this.initMinutes();
                          break;
                    }

                    this.moveNeedle();
                 }
              },
              _onOKClick: function ()
              {
				 if(this.isBeforeMaxDate(moment(this.currentDate), false, false) === false || this.isAfterMinDate(moment(this.currentDate), false, false) === false){
					 return;
				 }
				 
                 switch (this.currentView)
                 {
                    case 0:
                       if (this.params.time === true)
                       {
                          this.initHours();
                       } else
                       {
                          this.setElementValue();
                          this._onCloseClick();
                       }
                       break;
                    case 1:
                       this.initMinutes();
                       break;
                    case 2:
                       this.setElementValue();
                       this._onCloseClick();
                       break;
                 }
              },
              _onCancelClick: function ()
              {
                 if (this.params.time)
                 {
                    switch (this.currentView)
                    {
                       case 0:
                          this._onCloseClick();
                          break;
                       case 1:
                          if (this.params.date)
                          {
                             this.initDate();
                          } else
                          {
                             this._onCloseClick();
                          }
                          break;
                       case 2:
                          this.initHours();
                          break;
                    }
                 } else
                 {
                    this._onCloseClick();
                 }
              },
              _onYearChange: function (e)
              {
				 var selectYear = parseInt($(e.currentTarget).val());
                 this.currentDate.year(selectYear);
                 this.initDate(this.currentDate);
				 this.initYears();
              }
			  ,_onMonthChange: function (e)
              {
				 var selectMonth = parseInt($(e.currentTarget).val());
                 this.currentDate.month(selectMonth);
                 this.initDate(this.currentDate);
              },
			  _onMonthBeforeClick: function ()
              {
                 this.currentDate.subtract(1, 'months');
                 this.initDate(this.currentDate);
              },
              _onMonthAfterClick: function ()
              {
                 this.currentDate.add(1, 'months');
                 this.initDate(this.currentDate);
              },
              _onSelectDate: function (e)
              {
				 if($(e.currentTarget).attr("disabled")){
					 return;
				 }
                 this.$dtpElement.find('.picker__day--infocus').removeClass('picker__day--selected');
                 $(e.currentTarget).addClass('picker__day--selected');

                 this.selectDate(moment($(e.currentTarget).attr("data-date")));

                 if (this.params.switchOnClick === true && this.params.time === true)
                    setTimeout(this.initHours.bind(this), 200);

                 if(this.params.switchOnClick === true && this.params.time === false) {
                    setTimeout(this._onOKClick.bind(this), 200);
                 }

              },
              setDate: function (date)
              {
                 this.params.currentDate = date;
                 this.initDates();
              },
              setMinDate: function (date)
              {
                 this.params.minDate = date;
                 this.initDates();
              },
              setMaxDate: function (date)
              {
                 this.params.maxDate = date;
                 this.initDates();
              },
              destroy: function ()
              {
                 this._detachEvents();
                 this.$dtpElement.remove();
              }

           };
})(jQuery, moment);
