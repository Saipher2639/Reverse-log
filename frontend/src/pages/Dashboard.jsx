import { useEffect, useState } from "react";
import api from "../api/api";
function Dashboard() {
    const [history, setHistory] = useState([]);
    const [returns, setReturns] = useState([]);
    const [loading, setLoading] = useState(true);
    const [optimization, setOptimization] = useState(null);
    const [optimizing, setOptimizing] = useState(false);
    const [optimizationCosts, setOptimizationCosts] = useState({});
    const optimizeReturn = async (returnId) => {
        try {
            setOptimizing(true);

            const response = await api.post(
                `/optimization/optimize/${returnId}`
            );

            setOptimization(response.data);

            setOptimizationCosts((prev) => ({
                ...prev,
                [returnId]: response.data.totalCost
            }));

            setReturns((prevReturns) =>
                prevReturns.map((item) =>
                    item.id === returnId
                        ? { ...item, status: "ASSIGNED" }
                        : item
                )
            );

        } catch (error) {
            console.error("Optimization failed:", error);
            alert("Optimization failed");
        } finally {
            setOptimizing(false);
        }
    };
    const updateStatus = async (returnId, status) => {
        try {
            await api.put(
                `/returns/${returnId}/status?status=${status}`
            );

            setReturns((prevReturns) =>
                prevReturns.map((item) =>
                    item.id === returnId
                        ? { ...item, status: status }
                        : item
                )
            );

        } catch (error) {
            console.error("Status update failed:", error);
            alert("Status update failed");
        }
    };
    useEffect(() => {
        const fetchDashboardData = async () => {
            try {
                const [returnsResponse, historyResponse] = await Promise.all([
                    api.get("/returns"),
                    api.get("/optimization/history")
                ]);

                setReturns(returnsResponse.data);
                setHistory(historyResponse.data);

                const costs = {};

                historyResponse.data.forEach((item) => {
                    if (item.returnId) {
                        costs[item.returnId] = item.totalCost;
                    }
                });

                setOptimizationCosts(costs);
            } catch (error) {
                console.error("Failed to load dashboard:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchDashboardData();
    }, []);
    const activeReturns = returns.filter(
        (item) =>
            item.status === "CREATED" ||
            item.status === "ASSIGNED" ||
            item.status === "IN_TRANSIT" ||
            item.status === "PROCESSED"
    ).length;

    const pendingReturns = returns.filter(
        (item) => item.status === "CREATED"
    ).length;
    const completedReturns = returns.filter(
        (item) => item.status === "COMPLETED"
    ).length;

    const totalOptimized = history.length;

    const totalOptimizationCost = history.reduce(
        (sum, item) => sum + (item.totalCost || 0),
        0
    );

    const averageRouteCost =
        totalOptimized > 0
            ? totalOptimizationCost / totalOptimized
            : 0;
    return (
        <main className="p-8">

            <div className="grid grid-cols-4 gap-4 mb-8">

                <div className="bg-white border border-gray-200 rounded-lg p-5">
                    <p className="text-sm text-gray-500">Active Returns</p>
                    <p className="text-2xl font-semibold mt-2">{activeReturns}</p>
                </div>

                <div className="bg-white border border-gray-200 rounded-lg p-5">
                    <p className="text-sm text-gray-500">Pending Returns</p>
                    <p className="text-2xl font-semibold mt-2">{pendingReturns}</p>
                </div>

                <div className="bg-white border border-gray-200 rounded-lg p-5">
                    <p className="text-sm text-gray-500">Completed Returns</p>
                    <p className="text-2xl font-semibold mt-2">
                        {completedReturns}
                    </p>
                </div>
                <div className="bg-white border border-gray-200 rounded-lg p-5">
                    <p className="text-sm text-gray-500">Average Route Cost</p>
                    <p className="text-2xl font-semibold mt-2">
                        ₹{averageRouteCost.toFixed(0)}
                    </p>
                </div>
            </div>

            <div className="bg-white border border-gray-200 rounded-lg p-6 mb-8">

                <div className="flex justify-between items-center mb-6">

                    <div>
                        <h3 className="font-semibold text-gray-900">
                            Route Operations
                        </h3>

                        <p className="text-sm text-gray-500 mt-1">
                            Latest optimized route
                        </p>
                    </div>


                </div>

                {optimization ? (
                    <>
                        <div className="flex items-center gap-4 text-sm">

                            <div className="border border-gray-200 rounded-md px-4 py-3">
                                Return Location
                            </div>

                            <span className="text-gray-400">
                →
            </span>

                            <div className="border border-gray-200 rounded-md px-4 py-3">
                                {optimization.facility?.name || "Facility"}
                            </div>

                        </div>

                        <div className="grid grid-cols-3 gap-4 mt-6 pt-5 border-t border-gray-100">

                            <div>
                                <p className="text-xs text-gray-500">
                                    Distance
                                </p>

                                <p className="font-medium mt-1">
                                    {optimization.distance?.toFixed(2)} km
                                </p>
                            </div>

                            <div>
                                <p className="text-xs text-gray-500">
                                    Transport Cost
                                </p>

                                <p className="font-medium mt-1">
                                    ₹{optimization.transportCost?.toFixed(2)}
                                </p>
                            </div>

                            <div>
                                <p className="text-xs text-gray-500">
                                    Vehicle
                                </p>

                                <p className="font-medium mt-1">
                                    {optimization.vehicle?.vehicleNumber || "—"}
                                </p>
                            </div>

                        </div>
                    </>
                ) : (
                    <div className="py-8 text-center text-sm text-gray-500">
                        Optimize a return to see the best route here.
                    </div>
                )}

            </div>

            <div className="bg-white border border-gray-200 rounded-lg">

                <div className="px-6 py-5 border-b border-gray-200">
                    <h3 className="font-semibold">
                        Recent Returns
                    </h3>
                </div>

                <table className="w-full text-sm">

                    <thead className="bg-gray-50 text-gray-500">
                    <tr>
                        <th className="text-left px-6 py-3 font-medium">
                            Return
                        </th>
                        <th className="text-left px-6 py-3 font-medium">
                            Product
                        </th>
                        <th className="text-left px-6 py-3 font-medium">
                            Condition
                        </th>
                        <th className="text-left px-6 py-3 font-medium">
                            Location
                        </th>
                        <th className="text-left px-6 py-3 font-medium">
                            Status
                        </th>
                        <th className="text-right px-6 py-3 font-medium">
                            Cost
                        </th>
                        <th className="px-6 py-3 text-left">
                            Action
                        </th>
                    </tr>
                    </thead>

                    <tbody>
                    {loading ? (
                        <tr>
                            <td colSpan="7" className="px-6 py-8 text-center text-gray-500">
                                Loading returns...
                            </td>
                        </tr>
                    ) : returns.length === 0 ? (
                        <tr>
                            <td colSpan="7" className="px-6 py-8 text-center text-gray-500">
                                No returns found
                            </td>
                        </tr>
                    ) : (
                        returns.map((item) => (
                            <tr
                                key={item.id}
                                className="border-t border-gray-100"
                            >
                                <td className="px-6 py-4">
                                    #{item.id?.slice(-4)}
                                </td>

                                <td className="px-6 py-4">
                                    {item.product?.name || "—"}
                                </td>

                                <td className="px-6 py-4">
                                    {item.condition || "—"}
                                </td>

                                <td className="px-6 py-4">
                                    {item.customerLocation || "—"}
                                </td>
                                <td className="px-6 py-4">
    <span className="px-2.5 py-1 rounded-full text-xs font-medium bg-gray-100 text-gray-700">
        {item.status || "—"}
    </span>
                                </td>

                                <td className="px-6 py-4 text-right font-medium">
                                    {optimizationCosts[item.id] !== undefined
                                        ? `₹${optimizationCosts[item.id].toFixed(2)}`
                                        : "—"}
                                </td>
                                <td className="px-6 py-4">
                                    {item.status === "CREATED" && (
                                        <button
                                            onClick={() => optimizeReturn(item.id)}
                                            disabled={optimizing}
                                            className="px-4 py-2 rounded-lg bg-black text-white text-sm hover:bg-gray-800 disabled:opacity-50"
                                        >
                                            {optimizing ? "Optimizing..." : "Optimize"}
                                        </button>
                                    )}

                                    {item.status === "ASSIGNED" && (
                                        <button
                                            onClick={() => updateStatus(item.id, "IN_TRANSIT")}
                                            className="px-4 py-2 rounded-lg bg-black text-white text-sm hover:bg-gray-800"
                                        >
                                            Dispatch
                                        </button>
                                    )}

                                    {item.status === "IN_TRANSIT" && (
                                        <button
                                            onClick={() => updateStatus(item.id, "PROCESSED")}
                                            className="px-4 py-2 rounded-lg bg-black text-white text-sm hover:bg-gray-800"
                                        >
                                            Mark Processed
                                        </button>
                                    )}

                                    {item.status === "PROCESSED" && (
                                        <button
                                            onClick={() => updateStatus(item.id, "COMPLETED")}
                                            className="px-4 py-2 rounded-lg bg-black text-white text-sm hover:bg-gray-800"
                                        >
                                            Complete
                                        </button>
                                    )}

                                    {item.status === "COMPLETED" && (
                                        <span className="text-sm text-gray-400">
        Completed
    </span>
                                    )}
                                </td>
                            </tr>
                        ))
                    )}
                    </tbody>

                </table>
                {optimization && (
                    <div className="m-6 p-6 rounded-2xl bg-gray-50 border border-gray-200">

                        <div className="flex items-center justify-between mb-5">
                            <div>
                                <h3 className="text-lg font-semibold text-gray-900">
                                    Optimal Route
                                </h3>

                                <p className="text-sm text-gray-500">
                                    Best route selected by the optimizer
                                </p>
                            </div>

                            <button
                                onClick={() => setOptimization(null)}
                                className="text-gray-400 hover:text-gray-700"
                            >
                                ✕
                            </button>
                        </div>

                        <div className="grid grid-cols-2 md:grid-cols-3 gap-4">

                            <div className="bg-white p-4 rounded-xl border">
                                <p className="text-xs text-gray-500">
                                    Facility
                                </p>
                                <p className="font-semibold mt-1">
                                    {optimization.facility?.name}
                                </p>
                            </div>

                            <div className="bg-white p-4 rounded-xl border">
                                <p className="text-xs text-gray-500">
                                    Vehicle
                                </p>
                                <p className="font-semibold mt-1">
                                    {optimization.vehicle?.vehicleNumber}
                                </p>
                            </div>

                            <div className="bg-white p-4 rounded-xl border">
                                <p className="text-xs text-gray-500">
                                    Distance
                                </p>
                                <p className="font-semibold mt-1">
                                    {optimization.distance?.toFixed(2)} km
                                </p>
                            </div>

                            <div className="bg-white p-4 rounded-xl border">
                                <p className="text-xs text-gray-500">
                                    Transport Cost
                                </p>
                                <p className="font-semibold mt-1">
                                    ₹{optimization.transportCost?.toFixed(2)}
                                </p>
                            </div>

                            <div className="bg-white p-4 rounded-xl border">
                                <p className="text-xs text-gray-500">
                                    Processing Cost
                                </p>
                                <p className="font-semibold mt-1">
                                    ₹{optimization.processingCost?.toFixed(2)}
                                </p>
                            </div>

                            <div className="bg-white p-4 rounded-xl border">
                                <p className="text-xs text-gray-500">
                                    Total Cost
                                </p>
                                <p className="text-xl font-bold mt-1">
                                    ₹{optimization.totalCost?.toFixed(2)}
                                </p>
                            </div>

                        </div>
                    </div>
                )}

            </div>

        </main>
    );
}

export default Dashboard;