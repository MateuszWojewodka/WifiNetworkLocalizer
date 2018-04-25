using AutoMapper;
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
        [Route("rooms")]
        public IHttpActionResult GetPossibleRooms()
        {
            var data = _localizationServices.GetPossibleRooms();

            return Ok(data);
        }

        [HttpGet]
        [Route("rooms/{roomName}")] //?roomName=MCHTR
        public IHttpActionResult GetFourDeterminantMacIds([FromUri] string roomName)
        {
            var data = _localizationServices.GetFourDeterminantMacIds(roomName);

            return Ok(_mapperServices.Map<DeterminantMacIds, FourMacIds>(data));
        }

        [HttpPut]
        [Route("rooms/{roomName}")]
        public IHttpActionResult PutNewRoomDeterminantMacIds([FromUri] string roomName, [FromBody] DeterminantMacIds fourMacIds)
        {
            fourMacIds.RoomName = roomName;

            _localizationServices.SetFourMeasurmentMacIds(fourMacIds);

            return Ok();
        }

        [HttpPost]
        [Route("rooms/{id}/point")]
        public IHttpActionResult AddRSSIMeasurmentInXYPoint([FromUri] int id, [FromBody] RSSIMeasurmentPoint RSSIMeasurmentPoint)
        {
            RSSIMeasurmentPoint.RoomId = id;

            _localizationServices.AddRSSIMeasurmentInXYPoint(RSSIMeasurmentPoint);

            return Ok();
        }

        [HttpGet]
        [Route("rooms/{id}/point")] //?firstMacId=xxx&secondMacId=yyy&thirdMacId=zzz
        public IHttpActionResult GetNearestXYLocalizationPoint
            ([FromUri] int id, [FromUri] int firstMacId, [FromUri] int secondMacId, [FromUri] int thirdMacId, [FromUri] int fourthMacId)
        {
            var request = new FourRSSISignals
            {
                FirstMacIdRSSI = firstMacId,
                SecondMacIdRSSI = secondMacId,
                ThirdMacIdRSSI = thirdMacId,
                FourthMacIdRRSI = fourthMacId
            };

            var data = _localizationServices.GetNearestXYLocalizationPoint(id, request);

            return Ok(data);
        }
    }
}
