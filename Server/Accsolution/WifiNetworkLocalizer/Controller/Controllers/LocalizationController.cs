﻿using AutoMapper;
using Controller;
using Model.Database_Classes;
using Model.Entity_Classes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using WifiNetworkLocalizer.Model.Database_Handlers;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Controller
{
    [RoutePrefix("localization")]
    public class LocalizationController : ApiController
    {
        private ILocalization _localizationServices;
        private IMapper _mapperServices;

        public LocalizationController(ILocalization localizationServices, IMapper mapperServices)
        {
            _localizationServices = localizationServices;
            _mapperServices = mapperServices;
        }

        [HttpGet]
        [Route("point")] //?firstMacId=xxx&secondMacId=yyy&thirsMacId=zzz
        public IHttpActionResult GetNearestXYLocalizationPoint
            ([FromUri] int firstMacId, [FromUri] int secondMacId, [FromUri] int thirdMacId)
        {
            var request = new ThreeRSSISignals
            {
                FirstMacIdRSSI = firstMacId,
                SecondMacIdRSSI = secondMacId,
                ThirdMacIdRSSI = thirdMacId
            };

            var data = _localizationServices.GetNearestXYLocalizationPoint(request);

            return Ok(data);
        }

        [HttpGet]
        [Route("measurmentIds")] //?roomName=MCHTR KORYTARZ
        public IHttpActionResult GetThreeDeterminantMacIds([FromUri] string roomName)
        {
            var data = _localizationServices.GetThreeDeterminantMacIds(roomName);

            return Ok(_mapperServices.Map<DeterminantMacIds, ThreeMacIds>(data));
        }

        [HttpGet]
        [Route("buildings")]
        public IHttpActionResult GetPossibleRooms()
        {
            var data = _localizationServices.GetPossibleRooms();

            return Ok(data);
        }

        [HttpPost]
        [Route("measurmentIds")]
        public IHttpActionResult SetThreeMeasurmentMacIds([FromBody] DeterminantMacIds threeMacIds)
        {
            //var request = _mapperServices.Map<DeterminantMacIds, Model.Message_Types.DeterminantMacIds>(threeMacIds);
            _localizationServices.SetThreeMeasurmentMacIds(threeMacIds);

            return Ok();
        }

        [HttpPost]
        [Route("xyPoints")]
        public IHttpActionResult AddRSSIMeasurmentInXYPoint([FromBody] RSSIMeasurmentPoint RSSIMeasurmentPoint)
        {
            _localizationServices.AddRSSIMeasurmentInXYPoint(RSSIMeasurmentPoint);

            return Ok();
        }
    }
}
