using Model.Database_Classes;
using Model.Entity_Classes;
using Model.Logic;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;
using WifiNetworkLocalizer.Model.Database_Entities;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Model.Database_Handlers
{
    public class Localization : ILocalization
    {
        public Point GetNearestXYLocalizationPoint(int id, FourRSSISignals threeMacIds)
        {
            using (var ctx = new WifiLocalizerContext())
            {
                var nearestRSSIPoint = ctx.RSSIMeasurmentPoints.Where(x => x.RoomId == id)
                    .OrderBy(CalculteVectorsDiffExpression(threeMacIds))
                    .FirstOrDefault();

                DatabaseHandler.PrintSuccesFetchingFromDatabase(nearestRSSIPoint);

                return new Point
                {
                    x = nearestRSSIPoint.X,
                    y = nearestRSSIPoint.Y
                };
            }
        }

        public List<RoomInfo> GetPossibleRooms()
        {
            using (var ctx = new WifiLocalizerContext())
            {
                List<RoomInfo> returnList = new List<RoomInfo>();

                var selectResult = ctx.DeterminantMacIds.Select(x => new
                {
                    RoomId = x.Id,
                    RoomName = x.RoomName
                });

                foreach (var item in selectResult)
                {
                    returnList.Add(new RoomInfo
                    {
                        roomId = item.RoomId,
                        roomName = item.RoomName
                    });
                }

                DatabaseHandler.PrintSuccesFetchingFromDatabase("possible room list");

                return returnList;
            }
        }

        public DeterminantMacIds GetFourDeterminantMacIds(string roomName)
        {
            using (var ctx = new WifiLocalizerContext())
            {
                var result = ctx.DeterminantMacIds.Where(x => x.RoomName.Equals(roomName)).FirstOrDefault();

                DatabaseHandler.PrintSuccesFetchingFromDatabase(result);

                return result;
            }
        }

        public void SetFourMeasurmentMacIds(DeterminantMacIds fourMacIds)
        {
            using (var ctx = new WifiLocalizerContext())
            {
                DatabaseHandler.AddElementToDataBase(ctx, ctx.DeterminantMacIds, fourMacIds);
            }
        }

        public void AddRSSIMeasurmentInXYPoint(RSSIMeasurmentPoint RSSIMeasurmentPoint)
        {
            using (var ctx = new WifiLocalizerContext())
            {
                DeterminantMacIds determinantMacIds = ctx.DeterminantMacIds.Find(RSSIMeasurmentPoint.RoomId);

                RSSIMeasurmentPoint.DeterminantMacIds = determinantMacIds;
                DatabaseHandler.AddElementToDataBase(ctx, ctx.RSSIMeasurmentPoints, RSSIMeasurmentPoint);
            }
        }

        #region PRIVATE_METHODS

        private Expression<Func<RSSIMeasurmentPoint, double>> CalculteVectorsDiffExpression(FourRSSISignals fourMacIds)
        {
            return
                x =>
                //sqare root of
                (Math.Pow(
                    //second powers sum
                    Math.Pow((x.FirstMacIdRSSI - fourMacIds.FirstMacIdRSSI), 2) +
                    Math.Pow((x.SecondMacIdRSSI - fourMacIds.SecondMacIdRSSI), 2) +
                    Math.Pow((x.ThirdMacIdRSSI - fourMacIds.ThirdMacIdRSSI), 2) +
                    Math.Pow((x.FourthMacIdRSSI - fourMacIds.FourthMacIdRRSI), 2)
                    , 0.5));
        }

        #endregion
    }
}
